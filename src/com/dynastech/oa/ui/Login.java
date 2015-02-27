package com.dynastech.oa.ui;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

import com.dynastech.oa.AppContext;
import com.dynastech.oa.AppException;
import com.dynastech.oa.R;
import com.dynastech.oa.ui.adapter.AwesomePagerAdapter;
import com.dynastech.oa.ui.api.ApiClient;
import com.dynastech.oa.ui.common.Constant;
import com.dynastech.oa.ui.entity.User;
import com.dynastech.oa.ui.tasks.LoginTask;
import com.dynastech.oa.ui.widget.ScrollLayout;
import com.dynastech.oa.utils.Constants;
import com.dynastech.oa.utils.StringUtils;
import com.dynastech.oa.utils.UIHelper;
import com.dynastech.oa.utils.URLS;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Login extends BaseActivity implements OnClickListener,
		OnCheckedChangeListener {

	private RelativeLayout mLoginRelativeLayout, mAddressRelativeLayout;
	private ViewPager awesomePager;
	private EditText edit_username;
	private EditText edit_password;
	private EditText edit_ipaddress;
	private Button bt_login;
	private Button bt_setting;
	private Button bt_edit_username_clear;
	private Button bt_edit_pwd_clear;
	private CheckBox check_remember_pwd;
	private CheckBox check_auto_login;
	private View view_login, view_address;
	private AppContext appContext;
	private Context context;
	private InputMethodManager imm;
	private WifiManager wifi;
	private String macId;
	private int width, heght;
	private boolean isLogout;
	private static String host;
	private static int port;
	public static final int SUCCESS = 1;
	public static final int FAILED = -1;
	public static final int ERROR = 0;
	private List<View> mListViews;
	private AwesomePagerAdapter awesomeAdapter;
	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			if (msg.what == SUCCESS) {
				User user = (User) msg.obj;
				if (user != null) {
					UIHelper.ToastMessage(Login.this,
							R.string.msg_login_success);
					URLS.FORM_HTTP = edit_ipaddress.getText().toString();
					URLS.FORM_INFOPATH = edit_ipaddress.getText().toString()
							+ "/_layouts/oa/controls/InfoPath_Viewer.aspx?url="
							+ edit_ipaddress.getText().toString();
					String mFileServerHandlerUrl = user
							.getFileServerHandlerUrl();
					if (mFileServerHandlerUrl != null) {

						try {
							mFileServerHandlerUrl = mFileServerHandlerUrl
									.substring(0, mFileServerHandlerUrl
											.indexOf("/", 7))
									+ "/Files";
						} catch (Exception e) {
							e.printStackTrace();
						}
						URLS.ATTACHMENT_DOWNLOAD = mFileServerHandlerUrl;
						URLS.ATTACHMENT_ONLINEWATCH = mFileServerHandlerUrl;
					}

					startActivity(new Intent(context, Main.class));
					overridePendingTransition(R.anim.anim_fromleft_toup6,
							R.anim.anim_down_toright6);
					finish();
				}

			} else if (msg.what == FAILED) {
				((AppException) msg.obj).makeToast(Login.this);
			} else if (msg.what == ERROR) {
				Toast.makeText(context, R.string.login_error,
						Toast.LENGTH_SHORT).show();
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign);
		appContext = (AppContext) getApplication();
		context = this;
		// 初始化登陆界面
		initView();
		// 初始化登录数据
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		User user = appContext.initLoginInfo();
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		macId = wifi.getConnectionInfo().getMacAddress();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		heght = dm.heightPixels;
		isLogout = getIntent().getBooleanExtra(Constant._LOGOUT, false);
		if (user != null) {
			edit_username.setText(user.getUsername());
			if (user.isRememberMe()) {
				edit_password.setText(user.getPwd());
			}
			check_remember_pwd.setChecked(user.isRememberMe());
			check_auto_login.setChecked(user.isAutoLogin());
			if (isLogout) {
				bt_edit_username_clear.setVisibility(View.VISIBLE);
				bt_edit_pwd_clear.setVisibility(View.VISIBLE);
			}
			if (user.isAutoLogin() && !isLogout) {
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						loginMethod(null);
					}

				}, 1000);

			}

		}

	}

	// 初始化VIEW
	private void initView() {
		mListViews = new ArrayList<View>();
		view_login = LayoutInflater.from(context).inflate(R.layout.login, null);
		view_address = LayoutInflater.from(context).inflate(R.layout.ipadr,
				null);
		mListViews.add(view_login);
		mListViews.add(view_address);
		awesomeAdapter = new AwesomePagerAdapter(mListViews);
		awesomePager = (ViewPager) findViewById(R.id.awesomepager);
		awesomePager.setAdapter(awesomeAdapter);
		mLoginRelativeLayout = (RelativeLayout) view_login
				.findViewById(R.id.login_background);
		mAddressRelativeLayout = (RelativeLayout) view_address
				.findViewById(R.id.address_relative);
		edit_username = (EditText) view_login.findViewById(R.id.edit_username);
		edit_password = (EditText) view_login.findViewById(R.id.edit_password);
		edit_ipaddress = (EditText) view_address.findViewById(R.id.edit_host);
		// 判断配置文件里是否有，有就从配置文件读取，没得就读取默认的
		User _user = appContext.initLoginInfo();
		String ipaddress = _user.getAddress();

		if (ipaddress != null) {
			host = ipaddress.substring(7, ipaddress.length() - 5);
			port = Integer.parseInt(ipaddress.substring(ipaddress.length() - 4,
					ipaddress.length()));
			URLS.HOST = host;
			URLS.PORT = port;

			edit_ipaddress.setText(URLS.HTTP + host + ":" + port);
		} else {
			edit_ipaddress.setText(URLS.HTTP + URLS.HOST + ":" + URLS.PORT);
		}
		check_remember_pwd = (CheckBox) view_login
				.findViewById(R.id.login_rememberpwd);
		check_auto_login = (CheckBox) view_login.findViewById(R.id.login_auto);
		bt_login = (Button) view_login.findViewById(R.id.login);
		bt_setting = (Button) view_login.findViewById(R.id.login_setting);
		bt_edit_username_clear = (Button) view_login
				.findViewById(R.id.edit_username_clear);
		bt_edit_pwd_clear = (Button) view_login
				.findViewById(R.id.edit_pwd_clear);

		bt_edit_username_clear.setOnClickListener(this);
		bt_edit_pwd_clear.setOnClickListener(this);
		mLoginRelativeLayout.setOnClickListener(this);
		mAddressRelativeLayout.setOnClickListener(this);
		check_remember_pwd.setOnCheckedChangeListener(this);
		check_auto_login.setOnCheckedChangeListener(this);
		bt_login.setOnClickListener(this);
		bt_setting.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v == bt_edit_username_clear) {
			edit_username.getEditableText().clear();
			bt_edit_username_clear.setVisibility(View.GONE);

		} else if (v == bt_edit_pwd_clear) {
			edit_password.getEditableText().clear();
			bt_edit_pwd_clear.setVisibility(View.GONE);

		} else if (v == bt_login) {
			loginMethod(v);
		} else if (v == bt_setting) {
			awesomePager.setCurrentItem(1, true);
		} else if (v == mLoginRelativeLayout || v == mAddressRelativeLayout) {
			// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//
			// auto change the statues of hiding or show
			imm.hideSoftInputFromWindow(v.getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);// Always hide
		}

	}

	private void loginMethod(View v) {
		// TODO Auto-generated method stub
		String account = edit_username.getText().toString();
		String pwd = edit_password.getText().toString();
		String ipaddress = edit_ipaddress.getText().toString();

		boolean isRememberMe = check_remember_pwd.isChecked();
		boolean isAutoLogin = check_auto_login.isChecked();
		if (v != null)
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		if (StringUtils.isEmpty(account)) {
			UIHelper.ToastMessage(appContext,
					getString(R.string.msg_login_username_null));
			return;
		}
		if (StringUtils.isEmpty(pwd)) {
			UIHelper.ToastMessage(appContext,
					getString(R.string.msg_login_pwd_null));
			return;
		}
		if (StringUtils.isEmpty(ipaddress)) {
			UIHelper.ToastMessage(appContext,
					getString(R.string.msg_login_addr_null));
			return;
		}
		if (!appContext.isNetworkConnected()) {
			UIHelper.ToastMessage(this, R.string.network_not_connected);
			return;
		}
		bt_edit_username_clear.setVisibility(View.GONE);
		bt_edit_pwd_clear.setVisibility(View.GONE);

		String url = ipaddress + URLS._LOGIN_URL;
		host = ipaddress.substring(7, ipaddress.length() - 5);
		port = Integer.parseInt(ipaddress.substring(ipaddress.length() - 4,
				ipaddress.length()));
		Log.i("==========", host + "---" + port);
		URLS.HOST = host;
		URLS.PORT = port;
		if (macId == null) {
			macId = "pushto" + account;
		}
		new LoginTask(appContext, context, handler, isAutoLogin, isRememberMe,
				ipaddress).execute(url, account, pwd, width + "x" + heght,
				macId, android.os.Build.VERSION.RELEASE, "android");
		// macId
	}

	// dh GetMacAddress

	String getMac() {
		String macSerial = null;
		String str = "";
		try {
			Process pp = Runtime.getRuntime().exec(
					"cat /sys/class/net/wlan0/address ");
			InputStreamReader ir = new InputStreamReader(pp.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);

			for (; null != str;) {
				str = input.readLine();
				if (str != null) {
					macSerial = str.trim();// 去空格
					break;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return macSerial;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		if (buttonView == check_auto_login) {

			check_remember_pwd.setChecked(isChecked);
		}
		buttonView.setChecked(isChecked);
	}

}
