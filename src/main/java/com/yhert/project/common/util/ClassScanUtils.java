package com.yhert.project.common.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 扫描包 类以及方法
 * 
 * @author Ricardo
 *
 */
public class ClassScanUtils {
	// private static final Logger LOG =
	// LoggerFactory.getLogger(ScanUtils.class);

	/**
	 * 使用原本的大小写字母
	 */
	public static final int PROTOTYPE_CASE = 0;
	/**
	 * 大写字母
	 */
	public static final int UPPER_CASE = 1;
	/**
	 * 小写字母
	 */
	public static final int LOWER_CASE_LETTER = 2;

	/**
	 * jar文件常量
	 */
	private static final String JAR = "jar";
	/**
	 * file文件常量
	 */
	private static final String FILE = "file";
	/**
	 * class后缀常量
	 */
	private static final String CLASS_NAME = ".class";

	/**
	 * 扫描文件目录下的一层包目录
	 * 
	 * @param path
	 *            目录（com.YHeart）
	 * @return 返回当前目录下的所有目录结构
	 * @throws URISyntaxException
	 *             异常
	 * @throws IOException
	 *             异常
	 */
	public static Set<String> scanPacket(String path) throws URISyntaxException, IOException {
		path = suffe(path);
		String pathReplace = path.replaceAll("\\.", "/");
		URL url = Thread.currentThread().getContextClassLoader().getResource(pathReplace);
		Set<String> result = new HashSet<String>();
		if (null != url) {
			if (url.getProtocol().equals(FILE)) {
				result.addAll(scanPacketFile(path, url));
			} else if (url.getProtocol().equals(JAR)) {
				result.addAll(scanPacketJar(path, url));
			}
		}
		return result;
	}

	/**
	 * 扫描文件目录形式的Packet
	 * 
	 * @param path
	 *            路径
	 * @param url
	 *            Url路径
	 * @return 扫描结果
	 * @throws URISyntaxException
	 *             异常
	 */
	private static Set<String> scanPacketFile(String path, URL url) throws URISyntaxException {
		Set<String> result = new HashSet<String>();
		File file = new File(url.toURI());
		String[] s = file.list();
		for (int i = 0; i < s.length; i++) {
			if (!s[i].endsWith(CLASS_NAME))
				result.add(path + "." + s[i]);
		}
		return result;
	}

	/**
	 * 扫描Jar形式的Packet
	 * 
	 * @param path
	 *            路径
	 * @param url
	 *            URL路径
	 * @return 扫描结果
	 * @throws IOException
	 *             异常
	 */
	private static List<String> scanPacketJar(String path, URL url) throws IOException {
		String pathReplace = path.replaceAll("\\.", "/") + "/";
		List<String> result = new ArrayList<String>();
		JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
		Enumeration<JarEntry> enumeration = jar.entries();
		while (enumeration.hasMoreElements()) {
			JarEntry jarEntry = enumeration.nextElement();
			if (jarEntry.isDirectory()) {
				String name = jarEntry.getName();
				int index = name.indexOf(pathReplace);
				if (index != -1) {
					index = name.indexOf("/", index + pathReplace.length());
					if (index != -1) {
						if (name.length() == index + 1) {
							result.add(name.substring(0, name.length() - 1).replaceAll("/", "."));
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 扫描当前目录下的类
	 * 
	 * @param path
	 *            目录（com.YHeart）
	 * @return 然会当前包下的所有类
	 * @throws URISyntaxException
	 *             异常
	 * @throws IOException
	 *             异常
	 */
	public static List<Class<?>> scanClass(String path) throws URISyntaxException, IOException {
		path = suffe(path);
		String pathReplace = path.replaceAll("\\.", "/");
		List<Class<?>> result = new ArrayList<Class<?>>();
		URL url = Thread.currentThread().getContextClassLoader().getResource(pathReplace);
		if (url != null) {
			if (url.getProtocol().equals(FILE)) {
				result.addAll(scanClassFile(path, url));
			} else if (url.getProtocol().equals(JAR)) {
				result.addAll(scanClassJar(path, url));
			}
		}
		return result;
	}

	/**
	 * 扫描目录形式的Class
	 * 
	 * @param path
	 *            路径
	 * @param url
	 *            URL路径
	 * @return 扫描结果
	 * @throws URISyntaxException
	 *             异常
	 */
	private static List<Class<?>> scanClassFile(String path, URL url) throws URISyntaxException {
		path = suffe(path);
		List<Class<?>> result = new ArrayList<Class<?>>();
		File file = new File(url.toURI());
		String[] s = file.list();
		for (int i = 0; i < s.length; i++) {
			if (s[i].endsWith(CLASS_NAME)) {
				String classPath = path + "." + s[i];
				try {
					result.add(Class.forName(classPath.substring(0, classPath.length() - CLASS_NAME.length())));
				} catch (Throwable e) {
					// LOG.error("扫描包" + path + "抛出的错误", e);
				}
			}
		}
		return result;
	}

	/**
	 * 扫描jar形式的Class
	 * 
	 * @param path
	 *            路径
	 * @param url
	 *            URL路径
	 * @return 扫描结果
	 * @throws IOException
	 *             异常
	 */
	private static List<Class<?>> scanClassJar(String path, URL url) throws IOException {
		path = suffe(path);
		String pathReplace = path.replaceAll("\\.", "/") + "/";
		List<Class<?>> result = new ArrayList<Class<?>>();
		JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
		Enumeration<JarEntry> enumeration = jar.entries();
		while (enumeration.hasMoreElements()) {
			JarEntry jarEntry = enumeration.nextElement();
			if (!jarEntry.isDirectory()) {
				String name = jarEntry.getName();
				int index = name.indexOf(pathReplace);
				if (index != -1) {
					index = name.indexOf("/", index + pathReplace.length());
					if (index == -1) {
						if (name.endsWith(CLASS_NAME)) {
							name = name.replaceAll("/", ".");
							name = name.substring(0, name.length() - CLASS_NAME.length());
							try {
								result.add(Class.forName(name));
							} catch (Throwable e) {
								// LOG.error("扫描包" + path + "抛出的错误", e);
							}
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 扫描内部的所有方法
	 * 
	 * @param classes
	 *            需要扫描的类
	 * @param worldCase
	 *            生成的Key的大小写
	 * @return 扫描生成的map结构
	 */
	public static Map<String, Method> scanMethod(Class<?> classes, int worldCase) {
		Map<String, Method> map = new HashMap<String, Method>();
		Method[] methods = classes.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (ClassScanUtils.LOWER_CASE_LETTER == worldCase) {
				map.put(methods[i].getName().toLowerCase(), methods[i]);
			} else if (ClassScanUtils.UPPER_CASE == worldCase) {
				map.put(methods[i].getName(), methods[i]);
			} else {
				map.put(methods[i].getName(), methods[i]);
			}
		}
		return map;
	}

	/**
	 * 扫描内部的所有方法
	 * 
	 * @param classes
	 *            需要扫描的类
	 * @return 扫描生成的map结构
	 */
	public static Map<String, Method> scanMethod(Class<?> classes) {
		return ClassScanUtils.scanMethod(classes, ClassScanUtils.PROTOTYPE_CASE);
	}

	/**
	 * 扫描类内部的方法，并返回list对象
	 * 
	 * @param classes
	 *            需要扫描的类
	 * @return 扫描结果
	 */
	public static List<Method> scanMethodList(Class<?> classes) {
		List<Method> methodList = new ArrayList<Method>();
		Method[] methods = classes.getMethods();
		for (Method method : methods) {
			methodList.add(method);
		}
		return methodList;
	}

	/**
	 * 处理结尾符
	 * 
	 * @param path
	 *            路径
	 * @return 结果
	 */
	private static String suffe(String path) {
		if (path.endsWith(".")) {
			path = path.substring(0, path.length() - 1);
		} else if (path.endsWith(".*")) {
			path = path.substring(0, path.length() - 2);
		} else if ("*".equals(path)) {
			path = "";
		}
		return path;
	}

	/**
	 * 扫描包下面的所有类
	 * 
	 * @param paths
	 *            需要扫描的包名,多个包名逗号分隔
	 * @return 返回包下面的所有类
	 * @throws IOException
	 *             异常
	 */
	public static Set<Class<?>> scanAllClass(String paths) throws IOException {
		String[] pathArray = paths.split(",");
		Set<Class<?>> result = new HashSet<Class<?>>();
		for (String path : pathArray) {
			if (!StringUtils.isEmpty(path)) {
				path = suffe(path);
				String pathReplace = path.replaceAll("\\.", "/");
				Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(pathReplace);
				while (urls.hasMoreElements()) {
					URL url = urls.nextElement();
					if (url != null) {
						if (url.getProtocol().equals(FILE)) {
							result.addAll(scanAllClassFile(path, url));
						} else if (url.getProtocol().equals(JAR)) {
							result.addAll(scanAllClassJar(path, url));
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 扫描所有目录形式的类
	 * 
	 * @param path
	 *            路径
	 * @param url
	 *            URL路径
	 * @return 扫描结果
	 * @throws IOException
	 */
	private static List<Class<?>> scanAllClassFile(String path, URL url) throws IOException {
		path = suffe(path);
		String pathReplace = path.replaceAll("\\.", "/");
		List<Class<?>> result = new ArrayList<Class<?>>();
		Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(pathReplace);
		while (urls.hasMoreElements()) {
			try {
				File file = new File(urls.nextElement().toURI());
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					String classPath = path + "." + files[i].getName();
					if (files[i].isFile()) {
						if (files[i].getName().endsWith(CLASS_NAME)) {
							try {
								result.add(Class
										.forName(classPath.substring(0, classPath.length() - CLASS_NAME.length())));
							} catch (Throwable e) {
								// LOG.error("扫描包" + path + "抛出的错误", e);
							}
						}
					} else if (files[i].isDirectory()) {
						result.addAll(scanAllClassFile(classPath, url));
					}
				}
			} catch (Exception e) {
			}
		}
		return result;
	}

	/**
	 * 扫描所有jar形式的类
	 * 
	 * @param path
	 *            路径
	 * @param url
	 *            URL路径
	 * @return 扫描结果
	 * @throws IOException
	 *             异常
	 */
	private static List<Class<?>> scanAllClassJar(String path, URL url) throws IOException {
		path = suffe(path);
		String pathReplace = path.replaceAll("\\.", "/") + "/";
		List<Class<?>> result = new ArrayList<Class<?>>();
		JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
		Enumeration<JarEntry> enumeration = jar.entries();
		while (enumeration.hasMoreElements()) {
			JarEntry jarEntry = enumeration.nextElement();
			if (!jarEntry.isDirectory()) {
				String name = jarEntry.getName();
				int index = name.indexOf(pathReplace);
				if (index != -1) {
					if (name.endsWith(CLASS_NAME)) {
						name = name.replaceAll("/", ".");
						name = name.substring(0, name.length() - CLASS_NAME.length());
						try {
							result.add(Class.forName(name));
						} catch (ClassNotFoundException e) {
							// LOG.error("扫描包" + path + "抛出的错误", e);
						} catch (Throwable e) {
							// LOG.error("扫描包" + path + "抛出的错误", e);
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 扫描一个包类的所有类，并分析内部的方法
	 * 
	 * @param path
	 *            需要扫描的包
	 * @param worldCase
	 *            生成的Key的大小写
	 * @return 返回一个扫描后的map结构的数据
	 * @throws IOException
	 *             异常
	 */
	public static Map<Class<?>, Map<String, Method>> scanAllMethod(String path, int worldCase) throws IOException {
		Map<Class<?>, Map<String, Method>> result = new HashMap<Class<?>, Map<String, Method>>();
		Set<Class<?>> classesList = scanAllClass(path);
		for (Class<?> classes : classesList) {
			Map<String, Method> methodMap = scanMethod(classes, worldCase);
			result.put(classes, methodMap);
		}
		return result;
	}

	/**
	 * 扫描一个包类的所有类，并分析内部的方法
	 * 
	 * @param path
	 *            需要扫描的包
	 * @return 返回一个扫描后的map结构的数据
	 * @throws IOException
	 *             异常
	 */
	public static Map<Class<?>, Map<String, Method>> scanAllMethod(String path) throws IOException {
		return ClassScanUtils.scanAllMethod(path, ClassScanUtils.PROTOTYPE_CASE);
	}
}
