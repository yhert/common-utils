package com.yhert.project.common.util.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
	 * @param outputPath 输出路径
	 * @param paths      路径
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
	 * @param parentPath 路径
	 * @param file       被压缩文件
	 * @param zipOut     zip文件输出
	 * @throws Exception 异常
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
	 * @param path       zip文件路径
	 * @param outputPath 输出路径
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

	/**
	 * 字符串的压缩
	 * 
	 * @param str 待压缩的字符串
	 * @return 返回压缩后的字符串
	 * @throws IOException
	 */
	public static String compress(String str) {
		ZipOutputStream zipOutput = null;
		try {
			if (null == str || str.length() <= 0) {
				return str;
			}
			// 创建一个新的 byte 数组输出流
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 使用默认缓冲区大小创建新的输出流
			zipOutput = new ZipOutputStream(out);
			// 将 b.length 个字节写入此输出流
			zipOutput.write(str.getBytes("utf-8"));
			zipOutput.close();
			// 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
			return out.toString("ISO-8859-1");
		} catch (Exception e) {
			throw new ZipException("压缩字符串失败：" + str, e);
		} finally {
			if (zipOutput != null) {
				try {
					zipOutput.close();
				} catch (Exception e2) {
				}
			}
		}
	}

	/**
	 * 字符串的解压
	 * 
	 * @param str 对字符串解压
	 * @return 返回解压缩后的字符串
	 * @throws IOException
	 */
	public static String unCompress(String str) throws IOException {
		if (null == str || str.length() <= 0) {
			return str;
		}
		ZipInputStream zipInput = null;
		try {
			// 创建一个新的 byte 数组输出流
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
			ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
			// 使用默认缓冲区大小创建新的输入流
			zipInput = new ZipInputStream(in);
			byte[] buffer = new byte[256];
			int n = 0;
			while ((n = zipInput.read(buffer)) >= 0) {// 将未压缩数据读入字节数组
				// 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
				out.write(buffer, 0, n);
			}
			// 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
			return out.toString("utf-8");
		} catch (Exception e) {
			throw new ZipException("压缩字符串失败：" + str, e);
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
