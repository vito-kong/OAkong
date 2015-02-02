package com.dynastech.oa.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.dynastech.oa.AppContext;
import com.dynastech.oa.ui.entity.Downloader;
import com.dynastech.oa.ui.util.NetWorkHelper;

import android.content.Context;
import android.util.Log;

/**
 * apache的http处理
 * {@link #ApiClient}
 * DefaultHttpClient
 */
public class HttpUtils {
	public static String TAG = "HttpUtils";
	private static org.apache.http.client.HttpClient customerHttpClient;
	private static final String CHARSET_UTF8 = HTTP.UTF_8;

	public static String getJsonStringFromGZIP(HttpResponse response) {
		String jsonString = null;
		try {
			InputStream is = response.getEntity().getContent();
			BufferedInputStream bis = new BufferedInputStream(is);
			bis.mark(2);
			// 取前两个字节
			byte[] header = new byte[2];
			int result = bis.read(header);
			// reset输入流到开始位置
			bis.reset();
			// 判断是否是GZIP格式
			int headerData = getShort(header);
			// Gzip 流 的前两个字节是 0x1f8b
			if (result != -1 && headerData == 0x1f8b) {
				Log.i("HttpTask", " use GZIPInputStream  ");
				is = new GZIPInputStream(bis);
			} else {
				Log.i("HttpTask", " not use GZIPInputStream");
				is = bis;
			}
			InputStreamReader reader = new InputStreamReader(is, "utf-8");
			char[] data = new char[100];
			int readSize;
			StringBuffer sb = new StringBuffer();
			while ((readSize = reader.read(data)) > 0) {
				sb.append(data, 0, readSize);
			}
			jsonString = sb.toString();
			bis.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonString;
	}

	private static int getShort(byte[] data) {
		return (int) ((data[0] << 8) | data[1] & 0xFF);
	}

	/**
	 * GET方法，数据请求NTML
	 * @param url
	 * @param user
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public static String _get2(String url, String user, String pwd)
			throws Exception {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		// DefaultHttpClient httpclient=(DefaultHttpClient) getHttpClient();
		try {
			httpclient.getAuthSchemes().register(AuthPolicy.NTLM,
					new NTLMSchemeFactory());
			httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY,
					new NTCredentials(user, pwd, "myworkstation", URLS.DOMAIN));
			HttpHost target = new HttpHost(URLS.HOST, URLS.PORT, "http");
			HttpContext localContext = new BasicHttpContext();
			httpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 10000);
			httpclient.getParams().setParameter(
					ConnManagerParams.MAX_CONNECTIONS_PER_ROUTE, 50);
			httpclient.getParams().setParameter(
					ConnManagerParams.MAX_TOTAL_CONNECTIONS, 100);
			httpclient.getParams()
					.setParameter(ConnManagerParams.TIMEOUT, 5000);
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8092);
			HttpGet httpget = new HttpGet(url);
			httpget.addHeader("Accept-Encoding", "gzip,deflate,sdch");
			HttpResponse response = httpclient.execute(target, httpget,
					localContext);

			if (response.getStatusLine().getStatusCode() == 200) {
				for (Header header : response.getHeaders("Content-Encoding")) {
					if ("gzip".equals(header.getValue())) {
						return getJsonStringFromGZIP(response);
					}
				}
				return EntityUtils.toString(response.getEntity());
			}
		} finally {
			httpclient.getConnectionManager().shutdown();

		}
		return null;

	}

	/**
	 * GET方法，数据请求basic
	 * @param url
	 * @param user
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public static String _get_basic(String url, String user, String pwd)
			throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();//
		try {
			httpclient.getCredentialsProvider().setCredentials(
					new AuthScope(URLS.HOST, URLS.PORT),
					new UsernamePasswordCredentials(user, pwd));

			HttpGet httpget = new HttpGet(url);//
			httpget.getParams().setParameter("http.connection.timeout", Integer.valueOf(20000));
			httpget.getParams().setParameter("http.socket.timeout", Integer.valueOf(20000));
			HttpResponse response = httpclient.execute(httpget);//
			HttpEntity entity = response.getEntity();//
			Log.i(TAG, "code:" + response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(entity);
			}

		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return null;
	}

	/**
	 * GET方法(NTML)
	 * @param url
	 * @param user
	 * @param pwd
	 * @param mContext
	 * @return
	 */
	public static String _get_ntml(String url, String user, String pwd,
			Context mContext) {
		HttpGet httpget=null;
		try {
			DefaultHttpClient httpclient = (DefaultHttpClient) getHttpClient(mContext);
			httpclient.getAuthSchemes().register(AuthPolicy.NTLM,
					new NTLMSchemeFactory());
			httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY,
					new NTCredentials(user, pwd, "myworkstation", URLS.DOMAIN));
			HttpHost target = new HttpHost(URLS.HOST, URLS.PORT, "http");
			HttpContext localContext = new BasicHttpContext();
			httpget = new HttpGet(url);
			httpget.addHeader("Accept-Encoding", "gzip,deflate,sdch");
			HttpResponse response;

			response = httpclient.execute(target, httpget, localContext);
			if (response.getStatusLine().getStatusCode() == 200) {
				for (Header header : response.getHeaders("Content-Encoding")) {
					if ("gzip".equals(header.getValue())) {
						return getJsonStringFromGZIP(response);
					}
				}
				return EntityUtils.toString(response.getEntity());
			}else{
				httpget.abort();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(httpget!=null){
				httpget.abort();
			}
			
		}
		return null;

	}

	/**
	 * 文件下载ntlm
	 * @param url
	 * @param filepath
	 * @param application
	 * @param user
	 * @param pwd
	 * @param mContext
	 * @return
	 * @throws Exception
	 */
	public static String _get_download_ntml(String url, String filepath,
			AppContext application, String user, String pwd,Context mContext) throws Exception {

		DefaultHttpClient httpclient = (DefaultHttpClient) getHttpClient(mContext);
		try {
			httpclient.getAuthSchemes().register(AuthPolicy.NTLM,
					new NTLMSchemeFactory());
			httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY,
					new NTCredentials(user, pwd, "myworkstation", URLS.DOMAIN));
			HttpHost target = new HttpHost(URLS.HOST, URLS.PORT, "http");
			HttpContext localContext = new BasicHttpContext();
			HttpGet httpget = new HttpGet(url);
			// HttpPost httpget=new HttpPost(url);
			HttpResponse response = httpclient.execute(target, httpget,
					localContext);
			Downloader d = application.getDownloaders().get(url);
			File temp = new File(filepath + ".temp");
			Log.i(TAG, "code:"+response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity httpEntity = response.getEntity();
				InputStream is = httpEntity.getContent();
				FileOutputStream fos = new FileOutputStream(temp);
				byte[] buffer = new byte[8192];
				int count = 0;
				int progress = 0;
				while ((count = is.read(buffer)) != -1) {
					fos.write(buffer, 0, count);
					progress = progress + count;
					d.setProgress(progress);
				}

				temp.renameTo(new File(filepath));
				fos.close();
				is.close();
				return "ok";
			}
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return "failed";

	}

	/**
	 * 文件下载basic
	 * @param url
	 * @param filepath
	 * @param appContext
	 * @param user
	 * @param pwd
	 * @param mContext
	 * @return
	 * @throws Exception
	 */
	public static String _get_download_basic(String url, String filepath,
			AppContext appContext, String user, String pwd,Context mContext) throws Exception {

			
			DefaultHttpClient httpclient = new DefaultHttpClient();
			try{
			httpclient.getCredentialsProvider().setCredentials(
					new AuthScope(URLS.HOST, URLS.PORT),
					new UsernamePasswordCredentials(user, pwd));
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			Downloader d = appContext.getDownloaders().get(url);
			File temp = new File(filepath + ".temp");

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = response.getEntity();
				InputStream is = httpEntity.getContent();
				FileOutputStream fos = new FileOutputStream(temp);
				byte[] buffer = new byte[1024 * 8];
				int count = 0;
				int progress = 0;
				while ((count = is.read(buffer)) != -1) {
					fos.write(buffer, 0, count);
					progress = progress + count;
					d.setProgress(progress);
				}

				temp.renameTo(new File(filepath));
				fos.close();
				is.close();
				return "ok";
			}
			}finally{
				httpclient.getConnectionManager().shutdown();
			}
		return "failed";

	}

	/**
	 * POST方法(NTML)
	 * @param url
	 * @param user
	 * @param pwd
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String _post_ntlm(String url, String user, String pwd,
			Map<String, String> params) throws Exception {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			httpclient.getAuthSchemes().register(AuthPolicy.NTLM,
					new NTLMSchemeFactory());
			httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY,
					new NTCredentials(user, pwd, "myworkstation", URLS.DOMAIN));
			httpclient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
			httpclient.getParams().setParameter(
					CoreConnectionPNames.SO_TIMEOUT, 20000);
			HttpHost target = new HttpHost(URLS.HOST, URLS.PORT, "http");
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
			// 发出HTTP request
			httppost.setEntity(new UrlEncodedFormEntity(p, HTTP.UTF_8));

			HttpResponse httpResponse = httpclient.execute(target, httppost,
					localContext);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(httpResponse.getEntity());
			}
		}

		finally {
			httpclient.getConnectionManager().shutdown();
		}

		return null;
	}

	private static org.apache.http.client.HttpClient httpclient = null;
	public static final int DEFAULT_SOCKET_TIMEOUT = 20000;
	public static final int DEFAULT_HOST_CONNECTIONS = 50;
	public static final int DEFAULT_MAX_CONNECTIONS = 100;
	public static final int DEFAULT_SOCKET_BUFFER_SIZE = 1024 * 10;

	private synchronized static org.apache.http.client.HttpClient getHttpClient() {
		if (httpclient == null) {
			HttpParams httpParams = new BasicHttpParams();

			// timeout: get connections from connection pool
			ConnManagerParams.setTimeout(httpParams, 5000);
			// timeout: connect to the server
			HttpConnectionParams.setConnectionTimeout(httpParams,
					DEFAULT_SOCKET_TIMEOUT);
			// timeout: transfer data from server
			HttpConnectionParams.setSoTimeout(httpParams,
					DEFAULT_SOCKET_TIMEOUT);

			// set max connections per host
			ConnManagerParams.setMaxConnectionsPerRoute(httpParams,
					new ConnPerRouteBean(DEFAULT_HOST_CONNECTIONS));
			// set max total connections
			ConnManagerParams.setMaxTotalConnections(httpParams,
					DEFAULT_MAX_CONNECTIONS);

			// use expect-continue handshake
			HttpProtocolParams.setUseExpectContinue(httpParams, true);
			// disable stale check
			HttpConnectionParams.setStaleCheckingEnabled(httpParams, false);

			HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(httpParams, HTTP.UTF_8);

			HttpClientParams.setRedirecting(httpParams, false);

			// set user agent
			String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
			HttpProtocolParams.setUserAgent(httpParams, userAgent);

			// disable Nagle algorithm
			HttpConnectionParams.setTcpNoDelay(httpParams, true);

			HttpConnectionParams.setSocketBufferSize(httpParams,
					DEFAULT_SOCKET_BUFFER_SIZE);

			// scheme: http and https
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schemeRegistry.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));

			ClientConnectionManager manager = new ThreadSafeClientConnManager(
					httpParams, schemeRegistry);
			httpclient = new DefaultHttpClient(manager, httpParams);
		}

		return httpclient;
	}

	private static synchronized org.apache.http.client.HttpClient getHttpClient(
			Context context) {
		if (null == customerHttpClient) {
			HttpParams params = new BasicHttpParams();
			// 设置一些基本参数
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, CHARSET_UTF8);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams
					.setUserAgent(
							params,
							"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
									+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
			// 超时设置
			/* 从连接池中取连接的超时时间 */
			ConnManagerParams.setTimeout(params, 5000);
			/* 连接超时 */
			int ConnectionTimeOut = 10000;
			if (!HttpUtils.isWifiDataEnable(context)) {
				ConnectionTimeOut = 20000;
			}
			HttpConnectionParams
					.setConnectionTimeout(params, ConnectionTimeOut);
			/* 请求超时 */
			HttpConnectionParams.setSoTimeout(params, 10000);
			// 设置我们的HttpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));

			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			customerHttpClient = new DefaultHttpClient(conMgr, params);
		}
		return customerHttpClient;
	}

	// 判断wifi网络是否可用
	public static boolean isWifiDataEnable(Context context) {
		String TAG = "httpUtils.isWifiDataEnable()";
		try {
			return NetWorkHelper.isWifiDataEnable(context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	// 设置Mobile网络开关
	public static void setMobileDataEnabled(Context context, boolean enabled) {
		String TAG = "httpUtils.setMobileDataEnabled";
		try {
			NetWorkHelper.setMobileDataEnabled(context, enabled);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
	}

	// 判断是否为漫游
	public static boolean isNetworkRoaming(Context context) {
		return NetWorkHelper.isNetworkRoaming(context);
	}
}
