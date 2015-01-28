package com.dynastech.cdmetro.newui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.DisplayType;
import it.sephiroth.android.library.imagezoom.test.utils.DecodeUtils;

import com.dynastech.cdmetro.AppContext;
import com.dynastech.oa.R;
import com.dynastech.cdmetro.newui.common.Constant;
import com.dynastech.cdmetro.newui.entity.User;
import com.dynastech.cdmetro.newui.util.DiskLruCache;
import com.dynastech.cdmetro.newui.util.ImageCache.ImageCacheParams;
import com.dynastech.cdmetro.newui.util.ImageFetcher;
import com.dynastech.cdmetro.newui.widget.RoundProgressBar;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.LruCache;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OnlineImage extends FragmentActivity {

	private String image_url, image_title, filepath, user;
	private Context mContext;
	private TextView title;
	private RoundProgressBar mRoundProgressBar;
	private ImageViewTouch mImage;
	private Matrix imageMatrix;
	private ImageView back;
	private AppContext appContext;
	private User _user;
	private LruCache<String, Bitmap> appBitmapCache;

	public synchronized LruCache<String, Bitmap> getBitmapCache() {
		if (appBitmapCache == null) {
			buildCache();
		}
		return appBitmapCache;
	}

	private void buildCache() {
		int memClass = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
				.getMemoryClass();

		int cacheSize = Math.max(1024 * 1024 * 8, 1024 * 1024 * memClass / 5);

		appBitmapCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {

				return bitmap.getByteCount();
			}
		};
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.online_image);
		mContext = this;
		appContext = (AppContext) getApplication();
		_user = appContext.initLoginInfo();
		user = _user.getUsername();
		Intent intent = getIntent();
		image_url = intent.getStringExtra(Constant._Online_Url);
		if (image_url == null)
			return;
		try {
			image_url = URLDecoder.decode(image_url, "utf-8");
			image_url = image_url.substring(0, image_url.lastIndexOf("/") + 1)
					+ URLEncoder
							.encode(image_url.substring(image_url
									.lastIndexOf("/") + 1), "utf-8");
			if (image_url.contains("+")) {
				image_url = image_url.replaceAll("\\+", "%20");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// image_url="http://oa.cdmetro.cn:30005/Files/OaFiles/Files/201401/809b50d7-71bb-40d5-835f-c9ccdaa604ab/5060/wallpaper_5245056.jpg";
		image_title = intent.getStringExtra(Constant._Title);
		mRoundProgressBar = (RoundProgressBar) findViewById(R.id.roundProgressBar);
		mImage = (ImageViewTouch) findViewById(R.id.online_image);
		mImage.setDisplayType(DisplayType.FIT_IF_BIGGER);
		back = (ImageView) findViewById(R.id.new_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		title = (TextView) findViewById(R.id.new_title);
		title.setText(image_title);
		String imagefolderpath = Constant._AttachFragment_Path + 
				File.separator;
		File imagefolder = new File(imagefolderpath);
		if (!imagefolder.exists()) {
			imagefolder.mkdirs();
		}
		filepath = imagefolderpath + image_title;
		Bitmap mBitmap = getBitmapCache().get(image_url);
		if (mBitmap != null) {
			mImage.setImageBitmap(mBitmap);
			mRoundProgressBar.setVisibility(View.GONE);
		} else if (new File(filepath).exists()) {
			putImageEnterCache(filepath, image_url);
			mRoundProgressBar.setVisibility(View.GONE);
		} else {
			new DownloadImgTask().execute(image_url, filepath);
		}

	}

	public class DownloadImgTask extends AsyncTask<String, Integer, String> {

		String key;

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HttpClient httpClient = new DefaultHttpClient();
			key = params[0];
			HttpGet httpGet = new HttpGet(params[0]);
			InputStream is = null;
			FileOutputStream baos = null;
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				long length = httpEntity.getContentLength();
				mRoundProgressBar.setMax((int) length);
				is = httpEntity.getContent();
				if (is != null) {
					baos = new FileOutputStream(params[1]);
					byte[] buf = new byte[1024 * 4];
					int read = -1;
					int count = 0;
					while ((read = is.read(buf)) != -1) {
						baos.write(buf, 0, read);
						count += read;
						publishProgress(count);
					}
					return params[1];
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (baos != null) {
						baos.close();
					}
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		protected void onProgressUpdate(Integer... progress) {
			// LogOut.out(this, "onProgressUpdate");
			mRoundProgressBar.setProgress(progress[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mRoundProgressBar.setVisibility(View.GONE);

			if (result != null) {
				putImageEnterCache(result, key);
			} else {
				mImage.setImageResource(R.drawable.empty_photo);
				Toast.makeText(mContext, "加载图片失败...", Toast.LENGTH_LONG).show();
			}

		}

	}

	public void putImageEnterCache(String result, String key) {
		Uri imageUri = Uri.fromFile(new File(result));
		int size = -1;
		// use the original image size
		Bitmap bitmap = DecodeUtils.decode(mContext, imageUri, size, size);
		if (bitmap != null) {
			// calling this will force the image to fit the ImageView
			// container width/height

			if (imageMatrix == null) {
				imageMatrix = new Matrix();
			} else {
				// get the current image matrix, if we want restore the
				// previous matrix once the bitmap is changed
				imageMatrix = mImage.getDisplayMatrix();
			}

			mImage.setImageBitmap(bitmap, imageMatrix.isIdentity() ? null
					: imageMatrix, ImageViewTouchBase.ZOOM_INVALID,
					ImageViewTouchBase.ZOOM_INVALID);
			getBitmapCache().put(key, bitmap);
		} else {
			mImage.setImageResource(R.drawable.empty_photo);
			Toast.makeText(mContext, "加载图片失败...", Toast.LENGTH_LONG).show();
		}
	}

}
