package com.dynastech.cdmetro.newui;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.dynastech.cdmetro.AppContext;
import com.dynastech.oa.R;
import com.dynastech.cdmetro.newui.common.Constant;
import com.dynastech.cdmetro.newui.entity.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class OnLineWatch extends BaseActivity implements OnGestureListener{

	private WebView web;
	private String online_url, title, user, pwd,_category;
	private String tag = "OnLineWatch";
	private AlertDialog dialog;
	private Context mContext;
	private ImageView back;
	private TextView text_title;
	private User _user;
	private AppContext appContext;
	private static final int FLING_MIN_DISTANCE = 120;// 移动最小距离
	private static final int FLING_MIN_VELOCITY = 200;// 移动最大速度
	private GestureDetector mygesture;
	private float scale_init;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {

			setContentView(R.layout.online_watch);
			mContext = this;
			mygesture = new GestureDetector(this,this);
			dialog = new AlertDialog.Builder(this).create();
			online_url = getIntent().getStringExtra(Constant._Online_Url);
			
			_category=getIntent().getStringExtra(Constant._Category);
			System.out.println("_category:"+_category);
			if(online_url==null)
				return;
			online_url = URLDecoder.decode(online_url, "utf-8");
			appContext = (AppContext) getApplication();
			_user = appContext.initLoginInfo();
			user = _user.getUsername();
			pwd = _user.getPwd();
			title = getIntent().getStringExtra(Constant._Title);
			web = (WebView) findViewById(R.id.online_webview);
			back = (ImageView) findViewById(R.id.new_back);
			back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});
		
			text_title = (TextView) findViewById(R.id.new_title);
			text_title.setText(title + "");
			web.getSettings().setJavaScriptEnabled(true);
			web.getSettings().setBuiltInZoomControls(true);
			web.getSettings().setUseWideViewPort(true);
			web.getSettings().setSupportZoom(true);
			web.getSettings().setDomStorageEnabled(true);
			web.getSettings().setAllowFileAccess(true);
			// web.getSettings().setPluginsEnabled(true);
			web.getSettings().setUseWideViewPort(true);
			// web.getSettings().setUserAgentString("Mozilla/5.0(iPad; U; CPU iPhone OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B314 Safari/531.21.10");
			// web.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_3 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11B511");
//			web.getSettings()
//					.setUserAgentString(
//							"Mozilla/5.0 (iPad; CPU OS 6_1_2 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Mobile/10B146");
			web.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_3 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B329 Safari/8536.25");
			web.requestFocus();
			web.getSettings().setLoadWithOverviewMode(true);
			scale_init = web.getScale();
			web.setWebViewClient(new WebViewClient() {

				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return true;
				}

				@Override
				public void onPageStarted(WebView view, String url,
						Bitmap favicon) {
					super.onPageStarted(view, url, favicon);
					
					if (!((Activity) mContext).isFinishing()) {
						dialog.show();
						Window window = dialog.getWindow();
						window.setContentView(R.layout.progress);
						TextView progressText = (TextView) window
								.findViewById(R.id.progress_text);
						progressText.setText("正在加载");
					}

				}

				@Override
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);
					if(!"ReportCenter".equals(_category)){
						web.loadUrl("javascript:var tables = document.getElementsByTagName('table'); tables[1].style.display='none'");
					}
					
					dialog.dismiss();
				}

				@Override
				public void onReceivedHttpAuthRequest(WebView view,
						HttpAuthHandler handler, String host, String realm) {
					// TODO Auto-generated method stub
					if("Documents".equals(_category)){
						handler.proceed("dgadmin", "Ad.Dguc.Com");
					}else{
						handler.proceed(user, pwd);
					}
					
					
				}

			});

			web.loadUrl(online_url);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			setResult(RESULT_OK);
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
				// 向左
				float scale = web.getScale();
				if (scale > scale_init)
					return false;

				if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
						&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
					Log.i(tag, "right");
					web.loadUrl("javascript:function go(next){var a = document.querySelector('#defaultform>:nth-child(4)>tbody>tr>td>:nth-child('+ (next ? 4 : 2) + ')');if (a.tagName === 'IMG') {return;}location.href = a.href;};go()");
				} // 向右
				if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
						&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
					Log.i(tag, "left");
					// web.loadUrl("javascript:function a(){location.href = document.querySelector('#defaultform table a:last-child').href};a();");
					web.loadUrl("javascript:function go(next){var a = document.querySelector('#defaultform>:nth-child(4)>tbody>tr>td>:nth-child('+ (next ? 4 : 2) + ')');if (a.tagName === 'IMG') {return;}location.href = a.href;};go(true)");
				}
				return false;
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(!"ReportCenter".equals(_category)){
			web.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					return mygesture.onTouchEvent(event);
				}
			});
		}
		
		return super.dispatchTouchEvent(ev);
	}
}
