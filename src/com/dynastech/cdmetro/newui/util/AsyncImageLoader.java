package com.dynastech.cdmetro.newui.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.dynastech.cdmetro.AppContext;
import com.dynastech.cdmetro.newui.entity.Downloader;
import com.dynastech.cdmetro.newui.entity.User;
import com.dynastech.cdmetro.utils.HttpUtils;
import com.dynastech.cdmetro.utils.URLS;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

public class AsyncImageLoader {

	private HashMap<String, SoftReference<Drawable>> imageCache;

	public AsyncImageLoader() {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	public Drawable loadDrawable(final AppContext appContext,final String imageUrl,
			final ImageCallback imageCallback) {
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		}
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
			}
		};
		new Thread() {
			@Override
			public void run() {
				Drawable drawable = loadImageFromUrl(appContext,imageUrl);
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
		return null;
	}

	public static Drawable loadImageFromUrl(AppContext appContext,String url) {
		
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		User _user = appContext.initLoginInfo();
		try {
			httpclient.getAuthSchemes().register(AuthPolicy.NTLM,
					new NTLMSchemeFactory());
			httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY,
					new NTCredentials(_user.getUsername(), _user.getPwd(), "myworkstation", URLS.DOMAIN));
			HttpHost target = new HttpHost(URLS.HOST, URLS.PORT, "http");
			HttpContext localContext = new BasicHttpContext();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(target, httpget,
					localContext);
	
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity httpEntity = response.getEntity();
				InputStream is = httpEntity.getContent();
				return Drawable.createFromStream(is, "scr");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return null;
		
		
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}

}
