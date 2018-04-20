package com.yhert.project.common.util.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import com.yhert.project.common.beans.Param;
import com.yhert.project.common.excp.NetException;
import com.yhert.project.common.util.BeanUtils;
import com.yhert.project.common.util.CommonFunUtils;
import com.yhert.project.common.util.StringUtils;
import com.yhert.project.common.util.file.IOUtils;

/**
 * http连接请求工具 Data: 2015-07-23
 * 
 * @author Ricardo
 *
 */
public class HttpUtils {

	static {
		// 连接池
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(200);
		commonClient = HttpClientBuilder.create().setConnectionManager(connManager).build();
	}

	/**
	 * 公共请求客户端，适用于无状态请求
	 */
	private static HttpClient commonClient;
	/**
	 * 访问地址
	 */
	public static final String USER_AGENT = "HttpClient: yhertCommon, www.yhert.com";
	public static final String PARAM_TYPE_FORM_DATA = "form-data";
	public static final String PARAM_TYPE_X_WWW_FORM_URLENCODED = "x-www-form-urlencoded";

	/**
	 * get请求
	 * 
	 * @param url
	 *            请求地址
	 * @return 获得结果
	 */
	public static String get(String url) {
		return getCommonHttpClientContext().get(url);
	}

	/**
	 * get请求
	 * 
	 * @param url
	 *            请求地址
	 * @param charset
	 *            编码方式
	 * @return 响应结果
	 */
	public static String get(String url, String charset) {
		return getCommonHttpClientContext().get(url, charset);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param data
	 *            请求数据
	 * @param charset
	 *            使用的编码方式
	 * @return 响应结果
	 */
	public static String post(String url, Map<String, String> data, String charset) {
		return getCommonHttpClientContext().post(url, data, charset);
	}

	/**
	 * 下载数据
	 * 
	 * @param url
	 *            地址
	 * @return 响应信息
	 */
	public static byte[] download(String url) {
		return getCommonHttpClientContext().download(url);
	}

	/**
	 * 获得公共的HttpClient
	 * 
	 * @return 公共请求
	 */
	public static HttpClient getCommonHttpClient() {
		return commonClient;
	}

	/**
	 * 获得公共的HttpClientContext对象，适用于无状态会话
	 * 
	 * @return 请求上下文
	 */
	public static HttpClientContext getCommonHttpClientContext() {
		return new HttpClientContext(commonClient);
	}

	/**
	 * 创建HttpClientContext，适用于有状态会话
	 * 
	 * @return 请求上下文
	 */
	public static HttpClientContext getHttpClientContext() {
		return new HttpClientContext();
	}

	/**
	 * 请求方法
	 * 
	 * @param method
	 *            方法
	 * @param url
	 *            地址
	 * @param entry
	 *            地址
	 * @param requestCallback
	 *            回调
	 */
	public static <T> T request(String method, String url, Object entry, ResponseCallback<T> requestCallback) {
		return getCommonHttpClientContext().request(method, url, entry, requestCallback);
	}

	/**
	 * 请求方法
	 * 
	 * @param method
	 *            方法
	 * @param url
	 *            地址
	 * @param entry
	 *            地址
	 * @param paramType
	 *            请求数据类型
	 * @param header
	 *            头信息
	 * @param callback
	 *            回调
	 */
	public static <T> T request(String method, String url, Object entry, ContentType paramType, Map<String, String> header, ResponseCallback<T> callback) {
		return getCommonHttpClientContext().request(method, url, entry, paramType, header, callback);
	}

	/**
	 * Http客户端上下文
	 * 
	 * @author Ricardo Li 2016年12月17日 下午6:58:50
	 *
	 */
	public static class HttpClientContext {
		private static HttpClient client = null;

		@SuppressWarnings("static-access")
		private HttpClientContext(HttpClient client) {
			this.client = client;
		}

		@SuppressWarnings("static-access")
		private HttpClientContext() {
			this.client = HttpClientBuilder.create().build();
		}

		/**
		 * get请求
		 * 
		 * @param url
		 *            请求路径
		 * @return 响应结果
		 */
		public String get(String url) {
			return this.get(url, "utf-8");
		}

		/**
		 * get请求
		 * 
		 * @param url
		 *            请求地址
		 * @param charset
		 *            编码方式
		 * @return 响应结果
		 */
		public String get(String url, String charset) {
			HttpGet get = new HttpGet(url);
			try {
				get.addHeader("User-Agent", USER_AGENT);
				HttpResponse response = client.execute(get);
				HttpEntity entity = response.getEntity();
				Header header = response.getFirstHeader("Content-Type");
				String type = header.getValue();
				if (!type.startsWith("text/html") && (!type.startsWith("text/plain")) && (!type.startsWith("application/x-javascript"))) {
					return null;
				}
				int start = 0;
				if ((start = type.indexOf("charset=")) != -1) {
					start = start + "charset=".length();
					charset = type.substring(start, type.length());
				}
				// System.out.println(type + charset);
				if (entity != null) {
					return CommonFunUtils.join(IOUtils.readLines(entity.getContent(), charset), "\n");
				} else {
					return null;
				}
			} catch (IOException e) {
				throw new NetException("请求地址 " + url + " 失败", e, url);
			} finally {
				get.releaseConnection();
				get.abort();
			}
		}

		/**
		 * post请求
		 * 
		 * @param url
		 *            请求地址
		 * @param data
		 *            请求数据
		 * @param charset
		 *            编码方式
		 * @return 响应结果
		 */
		public String post(String url, Map<String, String> data, String charset) {
			HttpPost post = new HttpPost(url);

			List<NameValuePair> datas = new ArrayList<NameValuePair>();
			Iterator<String> keyItr = data.keySet().iterator();
			while (keyItr.hasNext()) {
				String key = keyItr.next();
				String value = data.get(key);
				datas.add(new BasicNameValuePair(key, value));
			}
			try {
				HttpEntity entity = new UrlEncodedFormEntity(datas, charset);
				post.setEntity(entity);
				HttpResponse response = client.execute(post);
				entity = response.getEntity();
				if (entity != null) {
					return CommonFunUtils.join(IOUtils.readLines(entity.getContent(), charset), "\n");
				} else {
					return null;
				}
			} catch (IOException e) {
				throw new NetException("请求地址 " + url + " 失败", e, url);
			} finally {
				post.releaseConnection();
				post.abort();
			}
		}

		/**
		 * 下载数据
		 * 
		 * @param url
		 *            地址
		 * @return 响应信息
		 */
		public byte[] download(String url) {
			try {
				HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
				InputStream in = connection.getInputStream();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] b = new byte[1024];
				int length = 0;
				while ((length = in.read(b)) != -1) {
					out.write(b, 0, length);
				}
				out.flush();
				in.close();
				b = out.toByteArray();
				out.close();
				return b;
			} catch (Exception e) {
				throw new NetException(e);
			}
		}

		/**
		 * 请求方法
		 * 
		 * @param method
		 *            方法
		 * @param url
		 *            地址
		 * @param entry
		 *            地址
		 * @param callback
		 *            回调
		 */
		public <T> T request(String method, String url, Object entry, ResponseCallback<T> callback) {
			return request(method, url, entry, null, null, callback);
		}

		/**
		 * 请求方法
		 * 
		 * @param method
		 *            方法
		 * @param url
		 *            地址
		 * @param entry
		 *            地址
		 * @param header
		 *            头信息
		 * @param callback
		 *            回调
		 */
		public <T> T request(String method, String url, Object entry, ContentType paramType, Map<String, String> header, ResponseCallback<T> callback) {
			HttpResponse response = null;
			HttpRequestBase request = null;
			try {
				switch (method) {
				case HttpPost.METHOD_NAME:
					request = new HttpPost(url);
					break;
				case HttpPut.METHOD_NAME:
					request = new HttpPut(url);
					break;
				case HttpPatch.METHOD_NAME:
					request = new HttpPatch(url);
					break;
				case HttpDelete.METHOD_NAME:
					request = new HttpDelete(url);
					break;
				default:
					request = new HttpGet();
					break;
				}
				request.setURI(URI.create(url));
				header = header == null ? new HashMap<>() : header;
				if (!header.containsKey("Accept")) {
					header.put("Accept", "application/json");
				} else {
					if (StringUtils.isEmpty(header.get("Accept"))) {
						header.remove("Accept");
					}
				}
				if (!header.containsKey("Content-Type")) {
					header.put("Content-Type", "application/json");
				} else {
					if (StringUtils.isEmpty(header.get("Content-Type"))) {
						header.remove("Content-Type");
					}
				}
				if (request instanceof HttpEntityEnclosingRequestBase) {
					if (entry != null) {
						if (ContentType.APPLICATION_FORM_URLENCODED.equals(paramType)) {
							// request.addHeader("Content-Type",
							// "application/x-www-form-urlencoded");
							// StringBuilder psb = new StringBuilder();
							Param pMap = BeanUtils.copyObject(entry, Param.class);
							List<NameValuePair> datas = new ArrayList<NameValuePair>();
							Iterator<String> keyItr = pMap.keySet().iterator();
							while (keyItr.hasNext()) {
								String key = keyItr.next();
								Object val = pMap.get(key);
								String value = val == null ? null : val.toString();
								datas.add(new BasicNameValuePair(key, value));
							}
							HttpEntity entity = new UrlEncodedFormEntity(datas);
							// for (Entry<String, Object> o : pMap.entrySet()) {
							// if (psb.length() > 0) {
							// psb.append("&");
							// }
							// psb.append(EncodeUtils.urlEncode(o.getKey().replaceAll("p_a_r_a_m_", "")));
							// psb.append("=");
							// if (o.getValue() != null) {
							// psb.append(EncodeUtils.urlEncode(o.getValue().toString()));
							// }
							// }
							// HttpEntity entity = new StringEntity(psb.toString(),
							// ContentType.APPLICATION_FORM_URLENCODED);
							HttpEntityEnclosingRequestBase.class.cast(request).setEntity(entity);
						} else {
							// request.addHeader("Content-Type",
							// "application/json");
							HttpEntity entity = new StringEntity(BeanUtils.object2Json(entry).replaceAll("p_a_r_a_m_", ""), ContentType.APPLICATION_FORM_URLENCODED);
							HttpEntityEnclosingRequestBase.class.cast(request).setEntity(entity);
						}
					}
				}
				for (Entry<String, String> he : header.entrySet()) {
					if (!StringUtils.isEmpty(he.getValue())) {
						request.addHeader(he.getKey(), he.getValue());
					}
				}
				response = client.execute(request);
				return callback.apply(response);
			} catch (Exception e) {
				throw new NetException("请求服务器出错", e);
			} finally {
				if (request != null) {
					request.releaseConnection();
				}
				if (response != null) {
					try {
						response.getEntity().getContent().close();
					} catch (Exception e) {
					}
				}
			}
		}
	}

	/**
	 * 请求回调函数
	 * 
	 * @author Ricardo Li 2017年10月28日 上午11:17:05
	 *
	 */
	public static interface ResponseCallback<T> {
		/**
		 * 响应处理
		 * 
		 * @param response
		 *            响应
		 * @return 返回值
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		default T apply(HttpResponse response) throws Exception {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return (T) CommonFunUtils.join(IOUtils.readLines(entity.getContent(), Charset.defaultCharset()), "\n");
			} else {
				return null;
			}
		}
	}
}
