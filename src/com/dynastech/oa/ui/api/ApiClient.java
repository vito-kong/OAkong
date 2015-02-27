package com.dynastech.oa.ui.api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.dynastech.oa.AppContext;
import com.dynastech.oa.AppException;
import com.dynastech.oa.ui.common.Constant;
import com.dynastech.oa.ui.entity.CategoryData;
import com.dynastech.oa.ui.entity.HomeData;
import com.dynastech.oa.ui.entity.Update;
import com.dynastech.oa.ui.entity.User;
import com.dynastech.oa.utils.URLS;

/**
 * 网络操作
 * 为喵post就用apche的HttpClient，get就用Android的HttpURLConnection
 * {@link #com.dynastech.oa.utils.HttpUtils}
 */
public class ApiClient {

	public static final String UTF_8 = "UTF-8";
	public static final String DESC = "descend";
	public static final String ASC = "ascend";

	private final static int TIMEOUT_CONNECTION = 10000;
	private final static int TIMEOUT_SOCKET = 10000;
	private final static int RETRY_TIME = 2;
	private static String tag = "ApiClient";
	public static final int DEFAULT_SOCKET_TIMEOUT = 10000;
	public static final int DEFAULT_HOST_CONNECTIONS = 50;
	public static final int DEFAULT_MAX_CONNECTIONS = 100;
	public static final int DEFAULT_SOCKET_BUFFER_SIZE = 1024 * 10;

	/**
	 * 需要认证
	 * @param url
	 * @param user
	 * @param pwd
	 * @param params
	 * @return
	 * @throws AppException
	 */
	public static String _post(String url, String user, String pwd,
			Map<String, String> params) throws AppException {

		int time = 0;
		do {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			try {
				httpclient.getAuthSchemes().register(AuthPolicy.NTLM,
						new NTLMSchemeFactory());
				httpclient.getCredentialsProvider().setCredentials(
						AuthScope.ANY,
						new NTCredentials(user, pwd, "myworkstation",
								URLS.DOMAIN));
				httpclient.getParams().setParameter(
						CoreConnectionPNames.CONNECTION_TIMEOUT,
						TIMEOUT_CONNECTION);
				httpclient.getParams().setParameter(
						CoreConnectionPNames.SO_TIMEOUT, TIMEOUT_SOCKET);
				HttpHost target = new HttpHost(URLS.HOST, URLS.PORT, "http");//
//				HttpHost target = new HttpHost(host, port, "http");//
				HttpContext localContext = new BasicHttpContext();
				HttpPost httppost = new HttpPost(url);

				List<NameValuePair> p = new ArrayList<NameValuePair>();
				if (params != null && !params.isEmpty()) {
					Set<Entry<String, String>> set = params.entrySet();
					Iterator<Entry<String, String>> it = set.iterator();
					while (it.hasNext()) {
						Entry<String, String> e = it.next();
						p.add(new BasicNameValuePair(e.getKey(), e.getValue()));
					}

				}
				httppost.setEntity(new UrlEncodedFormEntity(p, HTTP.UTF_8));
				HttpResponse httpResponse = httpclient.execute(target,
						httppost, localContext);
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				Log.i(tag, "statusCode:"+statusCode);
				String mEntityString=EntityUtils.toString(httpResponse.getEntity());
				Log.i(tag, "mEntityString:"+mEntityString);
				if (statusCode != HttpStatus.SC_OK) {
					throw AppException.http(statusCode);
				}
				return mEntityString;
			} catch (UnsupportedEncodingException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw AppException.http(e);

			} catch (ClientProtocolException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				httpclient.getConnectionManager().shutdown();
				httpclient = null;
			}
		} while (time < RETRY_TIME);

		return null;
	}

	/**
	 * 签到专用
	 * 不需要认证. 移动外勤系统不需要认证？
	 * @param url
	 * @param params
	 * @return
	 * @throws AppException
	 */
	public static String _post2(String url, Map<String, String> params)
			throws AppException {

		int time = 0;
		do {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			try {

				httpclient.getParams().setParameter(
						CoreConnectionPNames.CONNECTION_TIMEOUT,
						TIMEOUT_CONNECTION);
				httpclient.getParams().setParameter(
						CoreConnectionPNames.SO_TIMEOUT, TIMEOUT_SOCKET);
				HttpPost httppost = new HttpPost(url);

				List<NameValuePair> p = new ArrayList<NameValuePair>();
				if (params != null && !params.isEmpty()) {
					Set<Entry<String, String>> set = params.entrySet();
					Iterator<Entry<String, String>> it = set.iterator();
					while (it.hasNext()) {
						Entry<String, String> e = it.next();
						p.add(new BasicNameValuePair(e.getKey(), e.getValue()));
					}

				}
				httppost.setEntity(new UrlEncodedFormEntity(p, HTTP.UTF_8));
				HttpResponse httpResponse = httpclient.execute(httppost);
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					throw AppException.http(statusCode);
				}
				return EntityUtils.toString(httpResponse.getEntity());
			} catch (UnsupportedEncodingException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw AppException.http(e);

			} catch (ClientProtocolException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				httpclient.getConnectionManager().shutdown();
				httpclient = null;
			}
		} while (time < RETRY_TIME);

		return null;
	}

	//用户登录
	public static User login(AppContext appContext, String url, String account,
			String pwd, String Resolution, String MacId, String OSInfo,
			String Platform) throws AppException 
			{
		
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("UserName", account);
			params.put("Password", pwd);
			params.put("Resolution", Resolution);
			params.put("MacId", MacId);
			params.put("OSInfo", OSInfo);
			params.put("Platform", Platform);
			params.put("Token", MacId.replaceAll(":", ""));

			return User.parse(_post(url, account, pwd, params));
		} catch (Exception e) 
		{
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	//获取主页数据
	public static HomeData _getHomeData(String url, String account, String pwd)
			throws AppException {
		try {
			HomeData home_date = HomeData.get_HomeData();
			return home_date.parse(_post(url, account, pwd, null));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	//获取列表数据
	public static CategoryData _getCategoryData(User user, String _category,
			String _parent, int pageIndex, int pagesize, String domain)
			throws AppException {
		try {
			String url = user.getAddress() + URLS._CATEGORY_URL;
			String account = user.getUsername();
			String pwd = user.getPwd();
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", account);
			params.put("password", pwd);
			params.put("Category", _category);
			params.put("Parent", _parent);
			params.put("Page", pageIndex + "");
			params.put("Limit", pagesize + "");
			params.put("domain", domain);
			CategoryData category = CategoryData.get_CategoryData();
			return category.parse(_post(url, account, pwd, params));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * 检查版本更新
	 * 
	 * @param url
	 * @return
	 */
	public static Update checkVersion(AppContext appContext)
			throws AppException {
		try {
			return Update.parse(_get(URLS.UPDATE_VERSION));
		} catch (Exception e) {
			if (e instanceof AppException)
				throw (AppException) e;
			throw AppException.network(e);
		}
	}

	/**
	 * HttpURLConnection
	 * @param url
	 * @return
	 * @throws AppException
	 */
	public static String _get(String url) throws AppException {

		int time = 0;
		HttpURLConnection ucon = null;
		do {
			try {
				URL uri = new URL(url);
				ucon = (HttpURLConnection) uri.openConnection();
				ucon.setConnectTimeout(30000);
				ucon.setReadTimeout(30000);
				ucon.connect();
				InputStream is = ucon.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is);
				ByteArrayBuffer baf = new ByteArrayBuffer(1024);
				int current = 0;
				while ((current = bis.read()) != -1) {
					baf.append((byte) current);
				}
				return new String(baf.toByteArray(), "utf-8");
			} catch (MalformedURLException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				ucon.disconnect();
			}
		} while (time < RETRY_TIME);

		return null;

	}

	/**
	 * 获取网络图片
	 * 为喵获取网络图片不用defaultHttpClient
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getNetBitmap(String url) throws AppException {
		// System.out.println("image_url==> "+url);
		HttpClient httpClient = null;
		GetMethod httpGet = null;
		Bitmap bitmap = null;
		int time = 0;
		do {
			try {
				httpClient = getHttpClient();
				httpGet = getHttpGet(url, null, null);
				int statusCode = httpClient.executeMethod(httpGet);
				if (statusCode != HttpStatus.SC_OK) {
					throw AppException.http(statusCode);
				}
				InputStream inStream = httpGet.getResponseBodyAsStream();
				bitmap = BitmapFactory.decodeStream(inStream);
				inStream.close();
				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				throw AppException.network(e);
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);
		return bitmap;
	}

	private static HttpClient getHttpClient() {
		HttpClient httpClient = new HttpClient();
		// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		// 设置 默认的超时重试处理策略
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置 连接超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(TIMEOUT_CONNECTION);
		// 设置 读数据超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(TIMEOUT_SOCKET);
		// 设置 字符集
		httpClient.getParams().setContentCharset(UTF_8);
		return httpClient;
	}

	private static GetMethod getHttpGet(String url, String cookie,
			String userAgent) {
		GetMethod httpGet = new GetMethod(url);
		// 设置 请求超时时间
		httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
		// httpGet.setRequestHeader("Host", URLs.HOST);
		httpGet.setRequestHeader("Connection", "Keep-Alive");
		httpGet.setRequestHeader("Cookie", cookie);
		httpGet.setRequestHeader("User-Agent", userAgent);
		return httpGet;
	}

	// public synchronized static DefaultHttpClient _getHttpClient() {
	// if(httpclient == null) {
	// HttpParams httpParams = new BasicHttpParams();
	//
	// // timeout: get connections from connection pool
	// ConnManagerParams.setTimeout(httpParams, 5000);
	// // timeout: connect to the server
	// HttpConnectionParams.setConnectionTimeout(httpParams,
	// DEFAULT_SOCKET_TIMEOUT);
	// // timeout: transfer data from server
	// HttpConnectionParams.setSoTimeout(httpParams, DEFAULT_SOCKET_TIMEOUT);
	//
	// // set max connections per host
	// ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new
	// ConnPerRouteBean(DEFAULT_HOST_CONNECTIONS));
	// // set max total connections
	// ConnManagerParams.setMaxTotalConnections(httpParams,
	// DEFAULT_MAX_CONNECTIONS);
	//
	// // use expect-continue handshake
	// HttpProtocolParams.setUseExpectContinue(httpParams, true);
	// // disable stale check
	// HttpConnectionParams.setStaleCheckingEnabled(httpParams, false);
	//
	// HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
	// HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);
	//
	// HttpClientParams.setRedirecting(httpParams, false);
	//
	// // set user agent
	// String userAgent =
	// "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
	// HttpProtocolParams.setUserAgent(httpParams, userAgent);
	//
	// // disable Nagle algorithm
	// HttpConnectionParams.setTcpNoDelay(httpParams, true);
	//
	// HttpConnectionParams.setSocketBufferSize(httpParams,
	// DEFAULT_SOCKET_BUFFER_SIZE);
	//
	// // scheme: http and https
	// SchemeRegistry schemeRegistry = new SchemeRegistry();
	// schemeRegistry.register(new Scheme("http",
	// PlainSocketFactory.getSocketFactory(), 80));
	// schemeRegistry.register(new Scheme("https",
	// SSLSocketFactory.getSocketFactory(), 443));
	//
	// ClientConnectionManager manager = new
	// ThreadSafeClientConnManager(httpParams, schemeRegistry);
	// httpclient = new DefaultHttpClient(manager, httpParams);
	// }
	//
	// return httpclient;
	// }
}
