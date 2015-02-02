package com.dynastech.oa.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dynastech.oa.AppContext;
import com.dynastech.oa.R;
import com.dynastech.oa.ui.api.ApiClient;
import com.dynastech.oa.ui.entity.LocationGson;
import com.dynastech.oa.ui.entity.LoginGson;
import com.dynastech.oa.ui.entity.SingGson;
import com.dynastech.oa.ui.entity.User;
import com.dynastech.oa.ui.tasks.SginLoginTask;
import com.dynastech.oa.ui.util.JsonUtils;
import com.dynastech.oa.utils.URLS;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Vito Kong
 * 签到功能
 * 该功能调用的是移动外勤系统的接口
 */
@SuppressLint("NewApi") 
	public class Sign extends BaseActivity implements View.OnClickListener {
	public static final int SIGN = 2;
	private String address;
	private AppContext appContext;
	private ImageView back;
	private EditText content;
	private Context ctx;
	private AlertDialog dialog;
	private Handler handler = new Handler() {
		public void handleMessage(Message paramMessage) {
			if (paramMessage.what == 2)
				Sign.this.login_gson = ((LoginGson) paramMessage.obj);
		}
	};
	private double latitude;
	private TextView location;
	private LoginGson login_gson;
	private double lontitude;
	private Map<String, String> params;
	private String pwd;
	private float radius;
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		public void onReceive(Context paramContext, Intent paramIntent) {
			if ("location".equals(paramIntent.getAction())) {
				Sign.this.address = paramIntent.getStringExtra("address");
				Sign.this.latitude = paramIntent.getDoubleExtra("latitude",
						104.06D);
				Sign.this.lontitude = paramIntent.getDoubleExtra("lontitude",
						30.670000000000002D);
				Sign.this.radius = paramIntent.getFloatExtra("radius", 100.0F);
				if ((Sign.this.address != null)
						&& (!"".equals(Sign.this.address.trim()))) {
					Sign.this.location.setText(Sign.this.address);
					Sign.this.send.setClickable(true);
				}
			}
		}
	};
	private Button send;
	private String sign_login_url;
	private String tag = "Sign";
	private String username;
	private Window window;

	public String getAppVersionName(Context paramContext) {
		String str = "";
		try {
			str = paramContext.getPackageManager().getPackageInfo(
					paramContext.getPackageName(), 0).versionName;
			if (str != null) {
				int i = str.length();
				if (i > 0)
					;
			} else {
				return "";
			}
		} catch (Exception localException) {
			Log.e("VersionInfo", "Exception", localException);
		}
		return str;
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		case R.id.signin_title:
			break;
		case R.id.signin_send:
			if (this.login_gson != null) {
				//这坨是个嘛东西？
				new SendTask().execute();
				return;
			}
			SginLoginTask localSginLoginTask = new SginLoginTask(this.handler);
			// Object[] arrayOfObject = new Object[4];
			// arrayOfObject[0] = this.sign_login_url;
			// arrayOfObject[1] = username;
			// arrayOfObject[2] = pwd;
			// arrayOfObject[3] = this.params;
			localSginLoginTask.execute(sign_login_url, params);
			Log.i("sign params", params.toString());
			Toast.makeText(this.ctx, "签到未登录成功,请重新尝试", Toast.LENGTH_SHORT).show();
			break;
		case R.id.signin_back:
			finish();
			break;
		}

	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.signin2);
		this.ctx = this;
		this.appContext = ((AppContext) getApplication());
		User localUser = this.appContext.initLoginInfo();
		this.username = localUser.getUsername();
		this.pwd = localUser.getPwd();
		this.dialog = new AlertDialog.Builder(this.ctx).create();
		this.location = ((TextView) findViewById(R.id.signin_location));
		this.content = ((EditText) findViewById(R.id.signin_edittext));
		this.send = ((Button) findViewById(R.id.signin_send));
		this.send.setOnClickListener(this);
		this.send.setClickable(false);
		IntentFilter localIntentFilter = new IntentFilter("location");
		registerReceiver(this.receiver, localIntentFilter);
		this.back = ((ImageView) findViewById(R.id.signin_back));
		this.back.setOnClickListener(this);
		String str = ((WifiManager) getSystemService("wifi"))
				.getConnectionInfo().getMacAddress();
		this.sign_login_url = URLS.LocationLogin;
		this.params = new HashMap<String, String>();
		this.params.put("LoginName", this.username);
		this.params.put("Password", this.pwd);
		this.params.put("macId", str);
		this.params.put("version", getAppVersionName(this));
		SginLoginTask localSginLoginTask = new SginLoginTask(this.handler);
		// Object[] arrayOfObject = new Object[4];
		// arrayOfObject[0] = this.sign_login_url;
		// arrayOfObject[1] = username;
		// arrayOfObject[2] = pwd;
		// arrayOfObject[3] = this.params;
		localSginLoginTask.execute(sign_login_url, params);
	}

	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(this.receiver);
	}

	@SuppressLint("NewApi") 
	class SendTask extends AsyncTask<String, Void, String> {
		SendTask() {
		}

		protected String doInBackground(String... paramArrayOfString) {
			try {
				LocationGson localLocationGson = new LocationGson();
				SingGson localSingGson = new SingGson();
				if ((Sign.this.latitude != 0.0D)
						&& (Sign.this.lontitude != 0.0D)
						&& (Sign.this.radius != 0.0D)) {
					localLocationGson.setBaiduY(Sign.this.latitude + "");
					localLocationGson.setBaiduX(Sign.this.lontitude + "");
					localLocationGson.setAccuracy(Sign.this.radius);
					localLocationGson.setPersonId(Sign.this.login_gson
							.getData().getUserInfo().getId());
					localLocationGson.setTime(new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss", Locale.CHINA)
							.format(new Date()));
				}
				localSingGson.setLocation(localLocationGson);
				localSingGson.setPersonId(Sign.this.login_gson.getData()
						.getUserInfo().getId());
				localSingGson.setText(Sign.this.content.getEditableText()
						.toString());
				String str1 = JsonUtils.parseObejectToString(localSingGson);
				HashMap<String, String> localHashMap = new HashMap<String, String>();
				localHashMap.put("sessionCode", Sign.this.login_gson.getData()
						.getSessionInfo().getSessionCode());
				localHashMap.put("type", "SignIn");
				localHashMap.put("content", str1);
				String str2 = ApiClient
						._post2(URLS.LocationSend,
								localHashMap);
				return str2;
			} catch (Exception localException) {
				localException.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String paramString) {
			super.onPostExecute(paramString);
			Log.i(Sign.this.tag, "result:" + paramString);
			Sign.this.dialog.dismiss();
			if ((paramString != null) && (!"".equals(paramString)))
				try {
					if (((Boolean) new JSONObject(paramString).get("Success"))
							.booleanValue()) {
						Toast.makeText(Sign.this.ctx, "签到成功",
								Toast.LENGTH_SHORT).show();
						Sign.this.finish();
						return;
					}
					Toast.makeText(Sign.this.ctx, "签到失败， 再次签到",
							Toast.LENGTH_SHORT).show();
					return;
				} catch (JSONException localJSONException) {
					localJSONException.printStackTrace();
					return;
				}
			Toast.makeText(Sign.this.ctx, "签到失败， 再次签到", Toast.LENGTH_SHORT)
					.show();
		}

		protected void onPreExecute() {
			super.onPreExecute();
			if (!((Activity) Sign.this.ctx).isFinishing()) {
				Sign.this.dialog.show();
				Sign.this.window = Sign.this.dialog.getWindow();
				Sign.this.window.setContentView(R.layout.progress);
				((TextView) Sign.this.window.findViewById(R.id.progress_text))
						.setText("数据提交中...");
				super.onPreExecute();
			}
		}
	}
}
