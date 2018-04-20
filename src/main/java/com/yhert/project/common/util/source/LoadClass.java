package com.yhert.project.common.util.source;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

//import java.lang.reflect.Method;

/**
 * 类加载器
 * 
 * @author Ricardo
 *
 */
public class LoadClass {
	/**
	 * 空构造函数
	 */
	private LoadClass() {
	}

	/**
	 * 动态加载类
	 * 
	 * @param urls
	 *            数据来源
	 * @param className
	 *            完整类名，如：cn.yheart.util.source.ClassLoader
	 * @return 类
	 * @throws ClassNotFoundException
	 *             异常
	 */
	public static Class<?> loadClassByUrl(URL[] urls, String className) throws ClassNotFoundException {
		@SuppressWarnings("resource")
		URLClassLoader urlClassLoader = new URLClassLoader(urls);
		return urlClassLoader.loadClass(className);
	}

	/**
	 * 动态动态加载类类
	 * 
	 * @param url
	 *            数据源
	 * @param className
	 *            完整类名，如：cn.yheart.util.source.ClassLoader
	 * @return 类
	 * @throws ClassNotFoundException
	 *             异常
	 */
	public static Class<?> loadClassByUrl(URL url, String className) throws ClassNotFoundException {
		return LoadClass.loadClassByUrl(new URL[] { url }, className);
	}

	/**
	 * 动态加载类
	 * 
	 * @param filePath
	 *            文件路径
	 * @param className
	 *            完整类名，如：cn.yheart.util.source.ClassLoader
	 * @return 类
	 * @throws ClassNotFoundException
	 *             异常
	 * @throws MalformedURLException
	 *             异常
	 */
	public static Class<?> loadClassByFile(String filePath, String className)
			throws ClassNotFoundException, MalformedURLException {
		return LoadClass.loadClassByUrl(new File(filePath).toURI().toURL(), className);
	}

	/**
	 * 动态加载类
	 * 
	 * @param urlPath
	 *            文件路径
	 * @param className
	 *            完整类名，如：cn.yheart.util.source.ClassLoader
	 * @return 类
	 * @throws ClassNotFoundException
	 *             异常
	 * @throws MalformedURLException
	 *             异常
	 */
	public static Class<?> loadClassByNetUrl(String urlPath, String className)
			throws ClassNotFoundException, MalformedURLException {
		return LoadClass.loadClassByUrl(new URL(urlPath), className);
	}

	/**
	 * 动态加载类，多文件地址加载，解决类之间管理问题
	 * 
	 * @param filePaths
	 *            文件路径
	 * @param className
	 *            完整类名，如：cn.yheart.util.source.ClassLoader
	 * @return 类
	 * @throws ClassNotFoundException
	 *             异常
	 * @throws MalformedURLException
	 *             异常
	 */
	public static Class<?> loadClassByFile(String[] filePaths, String className)
			throws ClassNotFoundException, MalformedURLException {
		URL[] urls = new URL[filePaths.length];
		for (int i = 0; i < filePaths.length; i++) {
			urls[i] = new File(filePaths[i]).toURI().toURL();
		}
		return LoadClass.loadClassByUrl(urls, className);
	}
}
