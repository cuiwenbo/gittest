package com.manong.mall.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

@SuppressWarnings("deprecation")
public class HttpKit {
	static boolean isProxy = false;

	/**
	 * configs 包含了请求Cookie,header中不包含Cookie
	 * 
	 * @throws HttpException
	 */
	public static HttpResult get(String url, JSONObject configs) throws HttpException {
		return connect(url, configs, null, 11);
	}

	public static HttpResult post(String url, String postbody, JSONObject configs) throws HttpException {
		return connect(url, configs, postbody, 22);
	}

	@SuppressWarnings("rawtypes")
	public static HttpResult post(String url, Map params, JSONObject configs) throws HttpException {
		return connect(url, configs, params, 21);
	}

	/**
	 * 
	 * @param url
	 * @param configs
	 * @param connectType
	 *            :11为get，21为post Map，22为post String
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static HttpResult connect(String url, JSONObject configs, Object postBody, int connectType)
			throws HttpException {
		System.out.println("请求开始：" + System.currentTimeMillis());
		url = url.trim();
		if (configs == null) {
			configs = new JSONObject();
		}
		Object oldCookie = configs.get("Cookie");
		HttpResult hr = new HttpResult();
		CloseableHttpResponse response = null;
		CookieStore cs = new BasicCookieStore();
		CloseableHttpClient httpClient = createHttpClient(url, cs);

		try {
			if (connectType == 11) {
				HttpGet get = new HttpGet(url);
				setRequestHeader(get, configs);
				response = httpClient.execute(get);
			} else {
				HttpPost post = new HttpPost(url);
				setRequestHeader(post, configs);

				String paramencode = "utf-8";
				if (configs.containsKey("paramencode"))
					paramencode = configs.getString("paramencode");
				if (connectType == 21) {
					List<NameValuePair> formparams = new ArrayList<NameValuePair>();
					Map params = (Map) postBody;
					if (params != null && params.size() > 0) {
						Set<String> keys = params.keySet();
						for (String key : keys) {
							formparams.add(new BasicNameValuePair(key, params.get(key).toString()));
						}
					}
					UrlEncodedFormEntity uefEntity;
					uefEntity = new UrlEncodedFormEntity(formparams, paramencode);
					post.setEntity(uefEntity);
				} else {
					post.setEntity(new StringEntity(postBody.toString()));
				}
				response = httpClient.execute(post);
			}
			hr.setReturnCode(response.getStatusLine().getStatusCode());
			boolean valid = hr.getReturnCode() == 200;
			if (!valid && configs.containsKey("validCode")) {
				int code = configs.getIntValue("validCode");
				valid = hr.getReturnCode() == code;
			}
			if (!valid) {
				hr.setErrMsg(false);
				throw new HttpException(hr.toString());
			}
			hr.setHeaderMap(getResponseHeaderMap(response));
			if (configs.containsKey("file") && configs.containsKey("filepath")) {
				String path = configs.getString("filepath");
				HttpEntity entity = response.getEntity();
				try {
					File storeFile = new File(path);
					FileOutputStream fos = new FileOutputStream(storeFile);
					entity.writeTo(fos);
					fos.close();
					hr.setReturnResult(path);
				} catch (Exception e) {
					hr.setReturnCode(900);
					hr.setErrMsg("保存文件失败:filepath");
					throw new HttpException(hr.toString());
				}
			} else if (configs.containsKey("filebuff")) {
				HttpEntity entity = response.getEntity();
				try {
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					entity.writeTo(bos);
					hr.setFilebuff(bos.toByteArray());
					bos.close();
				} catch (Exception e) {
					hr.setReturnCode(900);
					hr.setErrMsg("保存文件失败:filebuff");
					throw new HttpException(hr.toString());
				}
			} else {
				String resultencode = "utf-8";
				if (configs.containsKey("resultencode"))
					resultencode = configs.getString("resultencode");
				String retString = EntityUtils.toString(response.getEntity(), resultencode);
				hr.setReturnResult(retString);
			}
			Map<String, String> cookieMap = getCookies(oldCookie, cs.getCookies());
			hr.setCookieMap(cookieMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HttpException(hr.toString());
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
				}
			}
			try {
				httpClient.close();
			} catch (Exception e) {
			}
		}
		System.out.println("请求结束：" + System.currentTimeMillis());
		return hr;
	}

	/**
	 * 构造http请求客户端
	 * 
	 * @param url
	 * @param cs
	 * @return
	 */
	private static CloseableHttpClient createHttpClient(String url, CookieStore cs) {

		Builder requestBuilder = RequestConfig.custom().setSocketTimeout(30 * 1000).setConnectTimeout(30 * 1000)
				.setConnectionRequestTimeout(30 * 1000).setCookieSpec(CookieSpecs.STANDARD_STRICT);
		RequestConfig requestConfig = null;
		if (isProxy) {
			HttpHost host = new HttpHost("127.0.0.1", 8888, "http");
			requestConfig = requestBuilder.setProxy(host).build();
		} else {
			requestConfig = requestBuilder.build();
		}
		CloseableHttpClient httpClient = null;
		if (url.startsWith("https://")) {
			httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
					.setDefaultCookieStore(cs).setDefaultRequestConfig(requestConfig).build();
		} else {
			httpClient = HttpClients.custom().setDefaultCookieStore(cs).setDefaultRequestConfig(requestConfig).build();
		}
		return httpClient;
	}

	/**
	 * 写入请求头信息
	 */
	@SuppressWarnings("unchecked")
	private static void setRequestHeader(HttpRequestBase httpRequest, JSONObject configs) {
		Object o = configs.get("header");
		Map<String, String> header = null;
		if (o == null) {
			header = new HashMap<String, String>();
		} else {
			header = (Map<String, String>) o;
		}
		if (!header.containsKey("User-Agent")) {
			header.put("User-Agent", getUserAgent(0));
		}
		if (!header.containsKey("Accept")) {
			header.put("Accept", "*/*");
		}
		if (!header.containsKey("Accept-Language")) {
			header.put("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		}
		if (!header.containsKey("Accept-Encoding")) {
			header.put("Accept-Encoding", "gzip, deflate");
		}
		o = configs.get("Cookie");
		if (o != null) {
			header.put("Cookie", o.toString());
		}
		Set<String> headerKeys = header.keySet();
		for (String key : headerKeys) {
			String value = header.get(key);
			if (value == null || value.trim().length() == 0) {
				continue;
			}
			httpRequest.setHeader(key, value);
		}
	}

	/**
	 * TODO 获取UserAgent
	 * 
	 * @param type
	 *            0-pc firefox （default） 1-pc ie 2-pc chrome 3-iphone 自带浏览器
	 *            4-iphone 微信 5-android 6-android 微信
	 * @return
	 */
	private static String getUserAgent(int type) {
		String UserAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0";
		switch (type) {
		case 0:
			return UserAgent;
		case 1:
			return "User-Agent: Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Win64; x64; Trident/4.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E)";
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		default:
			break;
		}
		return UserAgent;
	}

	/**
	 * TODO 构造https连接
	 * 
	 * @return
	 */
	private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
		SSLConnectionSocketFactory sslsf = null;
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String arg0, SSLSocket arg1) throws IOException {

				}

				@Override
				public void verify(String arg0, X509Certificate arg1) throws SSLException {

				}

				@Override
				public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {

				}

			});
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return sslsf;
	}

	/**
	 * TODO 返回响应头Map
	 * 
	 * @param response
	 * 
	 * @return
	 */
	private static Map<String, String> getResponseHeaderMap(CloseableHttpResponse response) {
		Map<String, String> responseHeaderMap = new HashMap<String, String>();
		Header[] headers = response.getAllHeaders();
		if (headers == null) {
			return responseHeaderMap;
		}
		for (Header header : headers) {
			responseHeaderMap.put(header.getName(), header.getValue());
		}
		return responseHeaderMap;
	}

	/**
	 * TODO 叠加Cookie，新的覆盖旧的
	 * 
	 * @param oldCookie
	 *            请求Cookie
	 * @param cookieList
	 *            新增的Cookie
	 * @return
	 */
	private static Map<String, String> getCookies(Object oldCookie, List<Cookie> cookieList) {
		StringBuffer sb = new StringBuffer("");
		Map<String, String> map = new HashMap<String, String>();
		if (oldCookie != null) {
			sb.append(oldCookie);
			String[] strs = sb.toString().split(";");
			for (String s : strs) {
				int index = s.indexOf("=");
				if (index > -1) {
					map.put(s.substring(0, index).trim(), s.substring(index + 1));
				}
			}
		}
		String key = "";
		String value = "";
		if (cookieList != null) {
			for (Cookie c : cookieList) {
				key = c.getName().toUpperCase();
				if (key.indexOf("PATH") > -1 || key.indexOf("EXPIRES") > -1) {
					continue;
				}
				value = c.getValue();
				map.put(c.getName(), value);
			}
		}
		return map;
	}

	/**
	 * TODO 字符串Cookie转Map
	 * 
	 * @param cookie
	 * @return
	 */
	public static Map<String, String> cookieToMap(String cookie) {
		Map<String, String> cookieMap = new HashMap<String, String>();
		String[] cookies = cookie.split(";");
		if (cookies.length > 0) {
			for (String c : cookies) {
				int index = c.indexOf("=");
				cookieMap.put(c.substring(0, index).trim(), c.substring(index + 1));
			}
		}
		return cookieMap;
	}

	/**
	 * TODO Map Cookie转字符串Cookie
	 * 
	 * @param cookie
	 * @return
	 */
	public static String cookieMapToString(Map<String, String> cookieMap) {
		StringBuffer sb = new StringBuffer("");
		Set<String> keys = cookieMap.keySet();
		for (String key : keys) {
			sb.append(key + "=");
			sb.append(cookieMap.get(key) + ";");
		}
		return sb.toString();
	}

	/**
	 * 返回结果类
	 * 
	 * @author fzc
	 *
	 */
	public static class HttpResult {
		private int returnCode = 0;
		private String errMsg = "fail";
		private boolean isJsonResult = false;
		private int returnType = 1;// 1为html,2为json
		private String returnResult = "";
		private JSONObject returnResultJson = null;
		private String cookies = null;
		private Map<String, String> headerMap = null;// 响应头
		private Map<String, String> cookieMap = null;
		private byte[] filebuff = null;

		public int getReturnCode() {
			return returnCode;
		}

		public void setReturnCode(int returnCode) {
			this.returnCode = returnCode;
		}

		public boolean isJsonResult() {
			return isJsonResult;
		}

		public void setJsonResult(boolean isJsonResult) {
			this.isJsonResult = isJsonResult;
		}

		public int getReturnType() {
			return returnType;
		}

		public String getReturnResult() {
			return returnResult;
		}

		public void setReturnResult(String returnResult) {
			if (returnResult == null) {
				returnResult = "";
			}
			returnResult = returnResult.trim();
			this.returnResult = returnResult;
			this.isJsonResult = false;
			this.returnResultJson = null;
			this.returnType = 1;
			if (returnResult.startsWith("{") && returnResult.endsWith("}")) {
				try {
					JSONObject resJson = JSONObject.parseObject(returnResult);
					this.returnResultJson = resJson;
					this.isJsonResult = true;
					this.returnType = 2;
				} catch (Exception e) {

				}
			}
		}

		public JSONObject getReturnResultJson() {
			return returnResultJson;
		}

		public void setReturnResultJson(JSONObject returnResultJson) {
			this.returnResultJson = returnResultJson;
		}

		public String getCookies() {
			return cookies;
		}

		public Map<String, String> getHeaderMap() {
			return headerMap;
		}

		public void setHeaderMap(Map<String, String> headerMap) {
			this.headerMap = headerMap;
		}

		public Map<String, String> getCookieMap() {
			return cookieMap;
		}

		public void setCookieMap(Map<String, String> cookieMap) {
			this.cookieMap = cookieMap;
			this.cookies = cookieMapToString(cookieMap);
		}

		public byte[] getFilebuff() {
			return filebuff;
		}

		public void setFilebuff(byte[] filebuff) {
			this.filebuff = filebuff;
		}

		public String getErrMsg() {
			return errMsg;
		}

		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}

		public void setErrMsg(boolean rs) {
			if (rs) {
				this.errMsg = "success";
			} else {
				this.errMsg = "fail";
			}

		}

		@Override
		public String toString() {
			return "HttpResult [returnCode=" + returnCode + ", errMsg=" + errMsg + ", returnResult=" + returnResult
					+ ", cookies=" + cookies + "]";
		}

	}

	/**
	 * 访问失败异常
	 * 
	 * @author fzc
	 *
	 */
	public static class HttpException extends Exception {

		private static final long serialVersionUID = 6329511008571109108L;

		public HttpException() {
			super();
		}

		public HttpException(String message, Throwable cause) {
			super(message, cause);
		}

		public HttpException(String message) {
			super(message);
		}

		public HttpException(Throwable cause) {
			super(cause);
		}

	}
}
