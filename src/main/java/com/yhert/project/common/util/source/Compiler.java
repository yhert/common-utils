package com.yhert.project.common.util.source;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.FileDataSource;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

//import cn.yheart.rpc.comm.Hello;

/**
 * 动态编译类
 * 
 * @author Ricardo
 *
 */
public class Compiler {
	/** 包名 */
	private String packetName;
	/** 类名 */
	private String className;
	/** 源代码 */
	private String source;
	/** 输出地址 */
	private String outputPath;
	/** 源码地址 */
	private String sourcePath;
	/** 数据类型 */
	private Class<?> type;

	/** 提取包名称 */
	private Pattern packPattern = Pattern.compile("^package\\s+([a-z0-9.]+);");
	/** 提取类名称 */
	private Pattern classNamePattern = Pattern.compile("class\\s+([^{]+)");
	/** 提取类名称 */
	private Pattern interfaceNamePattern = Pattern.compile("interface\\s+([^{]+)");

	/**
	 * 构造方法
	 * 
	 * @param sourcePath
	 *            源码地址,默认编码：utf-8
	 * @param outputPath
	 *            编译后结果保存位置
	 * @throws IOException
	 *             异常
	 */
	public Compiler(String sourcePath, String outputPath) throws IOException {
		this(sourcePath, outputPath, "utf-8");
	}

	/**
	 * 构造方法
	 * 
	 * @param sourcePath
	 *            源码
	 * @param outputPath
	 *            编译后结果保存位置
	 * @param encoding
	 *            编码
	 * @throws IOException
	 *             异常
	 */
	public Compiler(String sourcePath, String outputPath, String encoding) throws IOException {
		this.sourcePath = sourcePath;
		this.outputPath = outputPath;
		this.loadSource(encoding);
		this.analyNews();
	}

	/**
	 * 获得包名
	 * 
	 * @return 包名
	 */
	public String getPacketName() {
		return packetName;
	}

	/**
	 * 获得类名
	 * 
	 * @return 类名
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * 获得源代码
	 * 
	 * @return 源代码
	 */
	public String getSource() {
		return source;
	}

	/**
	 * 获得输入路径
	 * 
	 * @return 输出路径
	 */
	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * 获得完整类名
	 * 
	 * @return 类名
	 */
	public String getCompleteClassName() {
		if (this.packetName != null) {
			return this.packetName + "." + this.className;
		} else {
			return this.className;
		}
	}

	/**
	 * 获得编译后的类型
	 * 
	 * @return 类型
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * 加载源码文件
	 * 
	 * @param encoding
	 *            编码
	 * @throws IOException
	 *             异常
	 */
	private void loadSource(String encoding) throws IOException {
		InputStream in = new FileDataSource(this.sourcePath).getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = 0;
		while ((i = in.read()) != -1) {
			baos.write(i);
		}
		baos.flush();
		byte[] b = baos.toByteArray();
		baos.close();
		this.source = new String(b, encoding);
	}

	/**
	 * 分析类名和包名
	 */
	private void analyNews() {
		Matcher matcher = this.packPattern.matcher(this.source);
		if (matcher.find()) {
			this.packetName = matcher.group(1).trim();
		}
		this.packPattern = null;
		matcher = this.classNamePattern.matcher(this.source);
		if (matcher.find()) {
			this.className = matcher.group(1).trim();
			int index = this.className.indexOf(" ");
			if (index != -1) {
				this.className = this.className.substring(0, index);
			}
		}
		if (this.className == null) {
			matcher = this.interfaceNamePattern.matcher(this.source);
			if (matcher.find()) {
				this.className = matcher.group(1).trim();
				int index = this.className.indexOf(" ");
				if (index != -1) {
					this.className = this.className.substring(0, index);
				}
			}
		}
		this.classNamePattern = null;
	}

	/**
	 * 编译对象
	 * 
	 * @return 结果
	 * @throws URISyntaxException
	 *             异常
	 */
	public boolean compiler() throws URISyntaxException {
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(null, null, null);
		Iterable<? extends JavaFileObject> fileObject = fileManager.getJavaFileObjects(this.sourcePath);
		Iterable<String> options = Arrays.asList("-d", this.outputPath);
		CompilationTask task = javaCompiler.getTask(null, fileManager, null, options, null, fileObject);
		return task.call();
	}

	/**
	 * 获得编译的源码的类
	 * 
	 * @param relyOnPath
	 *            依赖包的路径
	 * @throws MalformedURLException
	 *             异常
	 * @throws ClassNotFoundException
	 *             异常
	 */
	public void loadClass(String... relyOnPath) throws MalformedURLException, ClassNotFoundException {
		String[] paths = new String[relyOnPath.length + 1];
		System.arraycopy(relyOnPath, relyOnPath.length, paths, 0, relyOnPath.length);
		paths[relyOnPath.length] = this.outputPath;
		this.type = LoadClass.loadClassByFile(paths, this.getCompleteClassName());
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 *            参数
	 * @throws Exception
	 *             异常
	 *//*
		 * public static void main(String[] args) throws Exception { Compiler
		 * compiler = new Compiler("D:\\HelloImpl.java", "D:\\");
		 * System.out.println("==" + compiler.getPacketName() + "." +
		 * compiler.getClassName() + "==");
		 * System.out.println(compiler.getSource()); compiler.compiler();
		 * System.out.println("------compiler-----"); compiler.loadClass();
		 * Hello hello = (Hello) compiler.getType().newInstance();
		 * System.out.println(hello.say("carol")); }
		 */
}
