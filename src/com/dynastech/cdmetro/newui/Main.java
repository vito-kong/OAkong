package com.dynastech.cdmetro.newui;

import com.dynastech.cdmetro.AppContext;
import com.dynastech.cdmetro.AppException;
import com.dynastech.cdmetro.AppManager;
import com.dynastech.oa.R;
import com.dynastech.oa.entity.LoginGson;
import com.dynastech.oa.service.LocationService;
import com.dynastech.oa.ui.ContactList;
import com.dynastech.oa.ui.DocumentsList;
import com.dynastech.oa.ui.Sign;
import com.dynastech.cdmetro.beans.Data;
import com.dynastech.cdmetro.newui.adapter.MainGridAdapter;
import com.dynastech.cdmetro.newui.common.Constant;
import com.dynastech.cdmetro.newui.entity.HomeData;
import com.dynastech.cdmetro.newui.entity.User;
import com.dynastech.cdmetro.newui.tasks.MainPageAsynTask;
import com.dynastech.cdmetro.newui.util.UpdateManager;
import com.dynastech.cdmetro.newui.widget.ScrollLayout;
import com.dynastech.cdmetro.newui.widget.ScrollLayout.OnViewChangeListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Main extends BaseActivity {

	private String tag = "Main";
	private LoginGson login_gson;
	private ScrollLayout mScrollLayout;
	private AppContext appContext;
	private Context context;
	private Button home, setting;
	private boolean isRefresh;
	private long mExitTime;
	private String homeurl, username, pwd, displayname;
	private PullToRefreshGridView mPullRefreshGridView;
	private GridView mGridView;
	private TextView text_username, logout, checkupdate, qrcode;
	private MainGridAdapter adapter;
	private AlertDialog dialog_logout;
	public static final int SUCCESS = 1;
	public static final int FAILED = -1;
	public static final int ERROR = 0;
	public static final int JUMP_TO_CATEGORY = 1;
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			if (msg.what == SUCCESS) {
				HomeData result = (HomeData) msg.obj;
				adapter.setData(result.getData());
				adapter.notifyDataSetChanged();

			} else if (msg.what == FAILED) {
				((AppException) msg.obj).makeToast(Main.this);
			} else if (msg.what == ERROR) {
				Toast.makeText(context, R.string.msg_main_error,
						Toast.LENGTH_SHORT).show();
			}
			mPullRefreshGridView.onRefreshComplete();
		};
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == JUMP_TO_CATEGORY) {
			// isRefresh = true;
			mPullRefreshGridView.setRefreshing(true);
			// new MainPageAsynTask(appContext, context, handler, isRefresh)
			// .execute(homeurl, username, pwd);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);
		initData();
		initMainPage();
		initSettingPage();
		initServices();
	}

	private void initServices() {
		startService(new Intent(this.context, LocationService.class));
	}

	private void initMainPage() {
		// TODO Auto-generated method stub
		initFootBar();
		initGridList();
	}

	private void initSettingPage() {
		text_username = (TextView) findViewById(R.id.new_username);
		logout = (TextView) findViewById(R.id.new_logout);
		checkupdate = (TextView) findViewById(R.id.new_check_update);
		qrcode = (TextView) findViewById(R.id.qrcode);
		
		text_username.setText(displayname + "");

		dialog_logout = new AlertDialog.Builder(this).setMessage("确定退出吗")
				.setPositiveButton("取消", null)
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						startActivity(new Intent(appContext, Login.class)
								.putExtra(Constant._LOGOUT, true));
						finish();
					}
				}).create();

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog_logout.show();
			}
		});
	
		checkupdate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				UpdateManager.getUpdateManager().checkAppUpdate(context, true);
			}
		});

		qrcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(context, QrCodeActivity.class));
			}
		});
	}

	private void initData() {
		appContext = (AppContext) getApplication();
		context = this;
		User user = appContext.initLoginInfo();
		homeurl = user.getAddress() + Constant._HOME_URL;
		username = user.getUsername();
		displayname = user.getUserInfo().getDisplayName();
		pwd = user.getPwd();
		new MainPageAsynTask(appContext, context, handler, isRefresh).execute(
				homeurl, username, pwd);
		UpdateManager.getUpdateManager().checkAppUpdate(this, false);

	}

	private void initGridList() {
		mPullRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pull_refresh_grid);
		mPullRefreshGridView.setMode(Mode.PULL_FROM_START);
		mGridView = mPullRefreshGridView.getRefreshableView();
		mPullRefreshGridView.setScrollingWhileRefreshingEnabled(false);
		mPullRefreshGridView
				.setOnRefreshListener(new OnRefreshListener<GridView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<GridView> refreshView) {
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						isRefresh = true;
						new MainPageAsynTask(appContext, context, handler,
								isRefresh).execute(homeurl, username, pwd);
					}
				});
		adapter = new MainGridAdapter(context);
		mGridView.setAdapter(adapter);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Data data = (Data) parent.getAdapter().getItem(position);
				String category = data.getCategory();
				String content = data.getContent();
				String _parent = data.getParent();
				String title = data.getText();
				boolean enable = data.isEnabled();
				if ("WebPage".equals(category)) {
					startActivity(new Intent(context, OnLineWatch.class)
							.putExtra(Constant._Online_Url, content).putExtra(
									Constant._Title, title));

				} else if ("IM".equals(category)) {
					try {
						Intent intent = new Intent();
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setAction(Intent.ACTION_VIEW);
						intent.addCategory(Intent.CATEGORY_DEFAULT);// 应用过滤条件
						intent.setData(Uri.parse("lync://"));
						context.startActivity(intent);// 如果使用context启动Act的话需要加FLAG_ACTIVITY_NEW_TASK。

					} catch (ActivityNotFoundException e) {
						Toast.makeText(context, "找不到lync程序，请下载lync！",
								Toast.LENGTH_SHORT).show();
					}

				} else if ("Location".equals(category)) {
					Main.this.startActivity(new Intent(Main.this.context,
							Sign.class).putExtra("_login_gson",
							Main.this.login_gson));

				} else if ("Supervisor".equals(category)) {

					startActivity(new Intent(context, ContactList.class)
							.putExtra("_type", "root").putExtra("_id",
									data.getID() + ""));

				} else if ("Documents".equals(category)) {

					startActivity(new Intent(context, DocumentsList.class));

				} else if (enable) {
					Intent intent = new Intent(context, Category.class);
					intent.putExtra(Constant._Category, category);
					intent.putExtra(Constant._Title, title);
					intent.putExtra(Constant._Parent, _parent);
					startActivityForResult(intent, JUMP_TO_CATEGORY);
				}
			}
		});
	}

	/**
	 * 主页底部那两个切换坨坨
	 */
	private void initFootBar() {
		mScrollLayout = (ScrollLayout) findViewById(R.id.main_scrolllayout);
		mScrollLayout.setIsScroll(false);
		home = (Button) findViewById(R.id.main_footbar_home);
		setting = (Button) findViewById(R.id.main_footbar_setting);
		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// home.setBackgroundResource(R.drawable.home_selected_blue);
				// setting.setBackgroundResource(R.drawable.information);
				mScrollLayout.snapToScreen(0);

			}
		});
		setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// home.setBackgroundResource(R.drawable.home);
				// setting.setBackgroundResource(R.drawable.informmation_selected_blue);
				mScrollLayout.snapToScreen(1);
			}

		});
		home.setBackgroundResource(R.drawable.home_selected_blue);
		mScrollLayout.SetOnViewChangeListener(new OnViewChangeListener() {

			@Override
			public void OnViewChange(int viewIndex) {
				if (viewIndex == 0) {
					home.setBackgroundResource(R.drawable.home_selected_blue);
					setting.setBackgroundResource(R.drawable.information);
				} else if (viewIndex == 1) {
					home.setBackgroundResource(R.drawable.home);
					setting.setBackgroundResource(R.drawable.informmation_selected_blue);
				}

			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {

				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				AppManager.getAppManager().AppExit(context);
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
