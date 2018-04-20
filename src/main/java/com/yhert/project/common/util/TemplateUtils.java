package com.yhert.project.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yhert.project.common.excp.TemplateException;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 模板工具
 * 
 * @author Ricardo Li 2017年6月20日 上午10:58:56
 *
 */
public class TemplateUtils {

	/**
	 * 替换内容， @[key]
	 * 
	 * @param content
	 *            内容
	 * @param param
	 *            参数
	 * @return 处理后结果
	 */
	public static String templatedeal(String content, Map<String, Object> param) {
		return templatedeal(content, (x) -> {
			return param.get(x);
		});
	}

	/**
	 * 替换内容
	 * 
	 * @param content
	 *            内容
	 * @param param
	 *            参数
	 * @return 处理后结果
	 */
	public static String templatedeal(String content, TemplatedealCallback param) {
		if (StringUtils.isEmpty(content)) {
			return content;
		}
		Pattern pattern = Pattern.compile("(@\\[[A-Za-z0-9_\\.\\(\\)\\-]+\\])");
		Matcher matcher = pattern.matcher(content);
		StringBuilder result = new StringBuilder();
		int lastEnd = 0;
		while (matcher.find()) {
			// 处理SQL
			int start = matcher.start();
			int end = matcher.end();
			String word = matcher.group();
			result.append(content.substring(lastEnd, start));
			lastEnd = end;
			// 处理参数
			word = word.substring(2, word.length() - 1);
			Object val = param.get(word);
			if (null == val) {
				val = "";
			}
			result.append(val.toString());
		}
		result.append(content.substring(lastEnd, content.length()));
		return result.toString();
	}

	/**
	 * 模板功能模型
	 * 
	 * @author Ricardo Li 2017年6月5日 下午6:07:52
	 *
	 */
	public static interface TemplatedealCallback {
		/**
		 * 获得value
		 * 
		 * @param key
		 *            键
		 * @return 结果
		 */
		Object get(String key);
	}

	/**
	 * 通过字符串模板与内容输出字符串
	 * 
	 * @param tmpl
	 *            模板名称
	 * @return dataModel 数据模型
	 */
	public static String freemarkerTmpl2Str(String tmpl, Map<?, ?> dataModel) {
		ByteArrayOutputStream out = null;
		try {
			Configuration conf = new Configuration(Configuration.VERSION_2_3_23);
			conf.setTemplateLoader(new TemplateLoader() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Reader getReader(final Object templateSource, final String encoding) throws IOException {
					try {
						return (Reader) AccessController.doPrivileged(new PrivilegedExceptionAction() {
							public Object run() throws IOException {
								return new InputStreamReader(
										new ByteArrayInputStream(tmpl.toString().getBytes(encoding)), encoding);
							}
						});
					} catch (PrivilegedActionException e) {
						throw (IOException) e.getException();
					}
				}

				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public long getLastModified(final Object templateSource) {
					return ((Long) (AccessController.doPrivileged(new PrivilegedAction() {
						public Object run() {
							return new Date().getTime();
						}
					}))).longValue();
				}

				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Object findTemplateSource(final String name) throws IOException {
					try {
						return AccessController.doPrivileged(new PrivilegedExceptionAction() {
							public Object run() throws IOException {
								return name;
							}
						});
					} catch (PrivilegedActionException e) {
						throw (IOException) e.getException();
					}
				}

				@Override
				public void closeTemplateSource(final Object templateSource) throws IOException {
				}
			});
			Template template = conf.getTemplate(CommonFunUtils.getUUID(tmpl));
			out = new ByteArrayOutputStream();
			template.process(dataModel, new OutputStreamWriter(out));
			out.flush();
			byte[] bs = out.toByteArray();
			return new String(bs, "utf-8");
		} catch (Exception e) {
			throw new TemplateException("模板合成数据时出错", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 通过ftl文件模板与内容输出字符串
	 * 
	 * @param ftl
	 *            模板名称
	 * @return dataModel 数据模型
	 */
	public static String freemarkerFtl2Str(String ftl, Map<?, ?> dataModel) {
		ByteArrayOutputStream out = null;
		try {
			Configuration conf = new Configuration(Configuration.VERSION_2_3_23);
			conf.setTemplateLoader(new TemplateLoader() {

				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Reader getReader(final Object templateSource, final String encoding) throws IOException {
					try {
						return (Reader) AccessController.doPrivileged(new PrivilegedExceptionAction() {
							public Object run() throws IOException {
								if (templateSource instanceof File) {
									return new InputStreamReader(new FileInputStream((File) templateSource), encoding);
								} else if (templateSource instanceof String) {
									return new InputStreamReader(
											TemplateUtils.class.getResourceAsStream((String) templateSource));
								} else {
									throw new IllegalArgumentException("templateSource wasn't a File, but a: "
											+ templateSource.getClass().getName());
								}
							}
						});
					} catch (PrivilegedActionException e) {
						throw (IOException) e.getException();
					}
				}

				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public long getLastModified(final Object templateSource) {
					return ((Long) (AccessController.doPrivileged(new PrivilegedAction() {
						public Object run() {
							if (templateSource instanceof File) {
								return new Long(((File) templateSource).lastModified());
							} else {
								return new Date().getTime();
							}
						}
					}))).longValue();
				}

				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public Object findTemplateSource(final String name) throws IOException {
					try {
						return AccessController.doPrivileged(new PrivilegedExceptionAction() {
							public Object run() throws IOException {
								String filename = name;
								File file = new File(filename);
								if (file.exists()) {
									if (file.isFile()) {
										return file;
									} else {
										return null;
									}
								} else {
									if (!filename.startsWith("/")) {
										filename = "/" + filename;
									}
									InputStream inputStream = TemplateUtils.class.getResourceAsStream(filename);
									if (inputStream != null) {
										inputStream.close();
										return filename;
									} else {
										return null;
									}
								}
							}
						});
					} catch (PrivilegedActionException e) {
						throw (IOException) e.getException();
					}
				}

				@Override
				public void closeTemplateSource(final Object templateSource) throws IOException {
				}
			});
			// conf.setDirectoryForTemplateLoading(new File("."));
			Template template = conf.getTemplate(ftl);

			out = new ByteArrayOutputStream();
			template.process(dataModel, new OutputStreamWriter(out));
			out.flush();
			byte[] bs = out.toByteArray();
			return new String(bs, "utf-8");
		} catch (Exception e) {
			throw new TemplateException("模板合成数据时出错", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
