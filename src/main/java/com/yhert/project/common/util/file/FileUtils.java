package com.yhert.project.common.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.yhert.project.common.excp.FileException;
import com.yhert.project.common.util.StringUtils;

/**
 * 文件相关工具
 * 
 * @author Ricardo Li
 *
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	/**
	 * 模板文件前缀
	 */
	public static String TEMP_FILE_PRX = "java_tmp_";

	/**
	 * 切割符
	 */
	public static final String FILE_SEPARATOR = File.separator;

	/**
	 * 合并文件名
	 * 
	 * @param filepath
	 *            文件名前半段
	 * @param path
	 *            文件后半段路径
	 * @return 合成后的文件名
	 */
	public static String mergeFilePath(String filepath, String path) {
		if (filepath == null || path == null) {
			return null;
		}
		filepath = fileNameFormat(filepath);
		path = fileNameFormat(path);
		if (path.startsWith(FILE_SEPARATOR)) {
			path = path.substring(1, path.length());
		}
		if (!filepath.endsWith(FILE_SEPARATOR)) {
			filepath = filepath + FILE_SEPARATOR;
		}
		return filepath + path;
	}

	/**
	 * 文件路径标准化
	 * <p>
	 * 如：s/sdf\fe windows输出s\sdf\fe linux输出s/sdf/fe
	 * </p>
	 * 
	 * @param name
	 *            文件名
	 * @return 处理后数据
	 */
	public static String fileNameFormat(String name) {
		if (name == null)
			return null;
		StringBuilder sb = new StringBuilder();
		String[] tmps = name.split("/");
		boolean first = true;
		for (String tmp : tmps) {
			String[] ts = tmp.split("\\\\");
			for (String t : ts) {
				if (first)
					first = false;
				else
					sb.append(FILE_SEPARATOR);
				sb.append(t);
			}
		}
		String result = sb.toString();
		String lresult = "";
		while (!result.equals(lresult)) {
			lresult = result;
			if (FILE_SEPARATOR.equals("/")) { // 去掉/./这种模式的路径
				result = result.replace("/./", "/");
				result = result.replace("//", "/");
			} else {
				result = result.replace("\\.\\", "\\");
				result = result.replace("\\\\", "\\");
			}
		}
		if (FILE_SEPARATOR.equals("/")) { // 去掉/./这种模式的路径
			if (".".equals(result) || "./".equals(result) || "./.".equals(result))
				result = "./";
		} else {
			if (".".equals(result) || ".\\".equals(result) || ".\\.".equals(result))
				result = ".\\";
		}
		return result;
	}

	/**
	 * 打开文件,如果没有就在jar上下文查找
	 * 
	 * @param filename
	 *            文件
	 * @return 连接，没找到返回null
	 */
	public static InputStream openInputStream(String filename) {
		try {
			InputStream inputStream = null;
			File actionDefaultFile = new File(filename);
			if (actionDefaultFile.exists()) {
				inputStream = new FileInputStream(actionDefaultFile);
			} else {
				if (!filename.startsWith("/")) {
					filename = "/" + filename;
				}
				inputStream = FileUtils.class.getResourceAsStream(filename);
			}
			return inputStream;
		} catch (Exception e) {
			throw new FileException("初始化配置文件错误", e);
		}
	}

	/**
	 * 将输入流写到临时文件进行处理
	 * 
	 * @param inputStream
	 *            输入流
	 * @param callback
	 *            回调
	 */
	public static void inputStreamTempFileDeal(InputStream inputStream, InputStreamTempFileDealCallback callback) {
		inputStreamTempFileDeal(inputStream, "tmp", callback);
	}

	/**
	 * 将输入流写到临时文件进行处理
	 * 
	 * @param inputStream
	 *            输入流
	 * @param suffix
	 *            后缀名称
	 * @param callback
	 *            回调
	 */
	public static void inputStreamTempFileDeal(InputStream inputStream, String suffix,
			InputStreamTempFileDealCallback callback) {
		String name = "";
		if (!StringUtils.isEmpty(suffix)) {
			name = "." + suffix;
		}
		File file = null;
		try {
			file = File.createTempFile(TEMP_FILE_PRX, name);
			try (OutputStream outputStream = new FileOutputStream(file)) {
				IOUtils.copy(inputStream, outputStream);
				inputStream.close();
			}
			callback.apply(file);
		} catch (IOException e) {
			throw new FileException(e);
		} finally {
			if (file != null) {
				file.delete();
			}
		}
	}

	/**
	 * 模板功能模型
	 * 
	 * @author Ricardo Li 2017年6月5日 下午6:07:52
	 *
	 */
	public static interface InputStreamTempFileDealCallback {
		/**
		 * 获得value
		 * 
		 * @param key
		 *            键
		 * @return 结果
		 */
		default void apply(File file) {
		}
	}

	/**
	 * 扫描文件
	 * 
	 * @param file
	 *            文件
	 * @param suffix
	 *            后缀
	 * @return 结果
	 */
	public static List<File> scanFile(File file, String suffix) {
		return scanFile(file, new ScanFileSystemCallback() {

			@Override
			public boolean apply(File file) {
				if (StringUtils.isEmpty(suffix)) {
					return true;
				} else {
					return file.getName().endsWith(suffix);
				}
			}

		});
	}

	/**
	 * 扫描文件
	 * 
	 * @param file
	 *            文件
	 * @return 结果
	 */
	public static List<File> scanFile(File file) {
		return scanFile(file, new ScanFileSystemCallback() {
		});
	}

	/**
	 * 扫描文件
	 * 
	 * @param file
	 *            文件
	 * @param callback
	 *            回调
	 * @return 结果
	 */
	public static List<File> scanFile(File file, ScanFileSystemCallback callback) {
		return scanFileSystem(file, new ScanFileSystemCallback() {

			@Override
			public boolean apply(File file) {
				if (file.isDirectory()) {
					return false;
				} else {
					return callback.apply(file);
				}
			}
		});
	}

	/**
	 * 扫描目录
	 * 
	 * @param file
	 *            文件
	 * @param callback
	 *            回调
	 * @return 结果
	 */
	public static List<File> scanDirectory(File file) {
		return scanDirectory(file, new ScanFileSystemCallback() {
		});
	}

	/**
	 * 扫描目录
	 * 
	 * @param file
	 *            文件
	 * @param callback
	 *            回调
	 * @return 结果
	 */
	public static List<File> scanDirectory(File file, ScanFileSystemCallback callback) {
		return scanFileSystem(file, new ScanFileSystemCallback() {

			@Override
			public boolean apply(File file) {
				if (file.isFile()) {
					return false;
				} else {
					return callback.apply(file);
				}
			}
		});
	}

	/**
	 * 扫描文件系统
	 * 
	 * @param file
	 *            文件
	 * @param callback
	 *            回调
	 * @return 结果
	 */
	public static List<File> scanFileSystem(File file, ScanFileSystemCallback callback) {
		List<File> files = new ArrayList<>(10);
		if (file.exists()) {
			scanFileSystemDeep(file, files, callback);
		}
		return files;
	}

	/**
	 * 递归扫描
	 * 
	 * @param file
	 *            文件
	 * @param files
	 *            文件
	 * @param callback
	 *            回调
	 */
	private static void scanFileSystemDeep(File file, List<File> files, ScanFileSystemCallback callback) {
		for (File f : file.listFiles()) {
			if (callback.apply(f)) {
				files.add(f);
			}
			if (f.isDirectory()) {
				scanFileSystemDeep(f, files, callback);
			}
		}
	}

	/**
	 * 扫描文件回调
	 * 
	 * @author Ricardo Li 2017年10月22日 上午11:40:58
	 *
	 */
	public static interface ScanFileSystemCallback {
		/**
		 * 获得value
		 * 
		 * @param key
		 *            键
		 * @return 结果
		 */
		default boolean apply(File file) {
			return true;
		}
	}
}
