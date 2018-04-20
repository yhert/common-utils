package com.yhert.project.common.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

import com.yhert.project.common.excp.ZipException;

/**
 * 文件压缩与解压
 * 
 * @author Ricardo Li 2017年6月8日 上午10:23:52
 *
 */
public class ZipUtils {
	/**
	 * 压缩文件
	 * 
	 * @param outputPath
	 *            输出路径
	 * @param paths
	 *            路径
	 */
	public static void zip(String outputPath, String... paths) {
		ZipOutputStream zipOut = null;
		try {
			File zipFile = new File(outputPath);
			if (!zipFile.getParentFile().exists()) {
				zipFile.getParentFile().mkdirs();
			}
			zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
			for (String path : paths) {
				File file = new File(path);
				if (file.exists()) {
					zipE("", file, zipOut);
				}
			}
			zipOut.flush();
		} catch (Exception e) {
			throw new ZipException("压缩文件失败：" + Arrays.toString(paths) + " 到 " + outputPath, e);
		} finally {
			if (zipOut != null) {
				try {
					zipOut.close();
				} catch (Exception e2) {
				}
			}
		}
	}

	/**
	 * zip压缩
	 * 
	 * @param parentPath
	 *            路径
	 * @param file
	 *            被压缩文件
	 * @param zipOut
	 *            zip文件输出
	 * @throws Exception
	 *             异常
	 */
	private static void zipE(String parentPath, File file, ZipOutputStream zipOut) throws Exception {
		if (file.isFile()) {
			InputStream input = null;
			try {
				input = new FileInputStream(file);
				zipOut.putNextEntry(new ZipEntry(parentPath + file.getName()));
				IOUtils.copy(input, zipOut);
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (Exception e) {
					}
				}
			}
		} else if (file.isDirectory()) {
			String filePath = parentPath + file.getName() + "/";
			zipOut.putNextEntry(new ZipEntry(filePath));
			File[] childrens = file.listFiles();
			for (File children : childrens) {
				zipE(filePath, children, zipOut);
			}
		}
	}

	/**
	 * 解压
	 * 
	 * @param path
	 *            zip文件路径
	 * @param outputPath
	 *            输出路径
	 */
	public static void unzip(String path, String outputPath) {
		ZipInputStream zipInput = null;
		try {
			File output = new File(outputPath);
			if (!output.exists()) {
				output.mkdirs();
			}
			File file = new File(path);
			@SuppressWarnings("resource")
			ZipFile zipFile = new ZipFile(file);
			zipInput = new ZipInputStream(new FileInputStream(file));

			ZipEntry entry = null;
			while ((entry = zipInput.getNextEntry()) != null) {
				File outFile = new File(outputPath, entry.getName());
				if (entry.isDirectory()) {
					if (!outFile.exists()) {
						outFile.mkdirs();
					}
				} else {
					if (!outFile.exists()) {
						outFile.createNewFile();
					}
					InputStream input = null;
					OutputStream outputFile = null;
					try {
						input = zipFile.getInputStream(entry);
						outputFile = new FileOutputStream(outFile);
						IOUtils.copy(input, outputFile);
						outputFile.flush();
					} finally {
						if (input != null) {
							try {
								input.close();
							} catch (Exception e) {
							}
						}
						if (outputFile != null) {
							try {
								outputFile.close();
							} catch (Exception e) {
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ZipException("解压文件失败：" + path, e);
		} finally {
			if (zipInput != null) {
				try {
					zipInput.close();
				} catch (Exception e2) {
				}
			}
		}
	}
}
