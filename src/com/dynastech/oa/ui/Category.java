package com.dynastech.oa.ui;

import java.util.LinkedList;

import com.dynastech.oa.AppContext;
import com.dynastech.oa.AppException;
import com.dynastech.oa.R;
import com.dynastech.oa.beans.ContextBean;
import com.dynastech.oa.beans.LzList;
import com.dynastech.oa.ui.adapter.CategoryAdapter;
import com.dynastech.oa.ui.common.Constant;
import com.dynastech.oa.ui.entity.CategoryData;
import com.dynastech.oa.ui.entity.User;
import com.dynastech.oa.ui.tasks.CategoryTask;
import com.dynastech.oa.ui.tasks.ReadAsyncTask;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("HandlerLeak")
public class Category extends BaseActivity {

	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	private AppContext appContext;
	private Context context;
	private ImageView back;
	private TextView title;
	private String tag = "Category";
	private User user;
	private String _parent, _title, _category;
	private boolean isRefresh, isRequestNet;
	private int pageid = 1;
	private int state;
	public static final int LIMIT = 20;
	public static final int NORMAL = 0;
	public static final int PULL_DOWN = 1;
	public static final int PULL_UP = 2;
	public static final int JUMP_TO_MANAGER = 1;
	public static final int JUMP_TO_CATEGORY = 2;
	public static final int JUMP_TO_ONLINE = 3;
	private CategoryAdapter adapter;
	public static final int SUCCESS = 1;
	public static final int FAILED = -1;
	public static final int ERROR = 0;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (msg.what == SUCCESS) {
				CategoryData result = (CategoryData) msg.obj;
				int pagesize = 0;
				LinkedList<LzList> lzlists = result.getData();

				if (lzlists != null) {
					pagesize = lzlists.size();
					Log.i(tag, "pagesize:" + pagesize);
					adapter.setData(lzlists, state);
					adapter.notifyDataSetChanged();
					if (pagesize < LIMIT) {
						Toast.makeText(context, "数据加载完成", Toast.LENGTH_SHORT)
								.show();
					}
				}
			} else if (msg.what == FAILED) {
				((AppException) msg.obj).makeToast(Category.this);
			} else if (msg.what == ERROR) {
				Toast.makeText(context, R.string.msg_main_error,
						Toast.LENGTH_SHORT).show();
			}
			Log.i("adapter.getcount", adapter.getCount() + "");
			if (adapter.getCount() < LIMIT) {
				mPullRefreshListView.setMode(Mode.PULL_FROM_START);
			} else {
				mPullRefreshListView.setMode(Mode.BOTH);
			}
			mPullRefreshListView.onRefreshComplete();
		};

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		if (requestCode == JUMP_TO_MANAGER || requestCode == JUMP_TO_ONLINE
				|| requestCode == JUMP_TO_CATEGORY) {
			// String label = DateUtils.formatDateTime(
			// getApplicationContext(),
			// System.currentTimeMillis(),
			// DateUtils.FORMAT_SHOW_TIME
			// | DateUtils.FORMAT_SHOW_DATE
			// | DateUtils.FORMAT_ABBREV_ALL);

			// Update the LastUpdatedLabel
			// mPullRefreshListView.getLoadingLayoutProxy()
			// .setLastUpdatedLabel(label);
			// pageid = 1;
			// state = PULL_DOWN;
			// isRefresh = true;
			// isRequestNet = true;
			// new CategoryTask(appContext, context, handler,
			// isRefresh).execute(user, _category,
			// _parent == null ? "" : _parent, pageid,
			// isRequestNet,"oa");
			mPullRefreshListView.getRefreshableView().setSelection(0);
			mPullRefreshListView.setMode(Mode.PULL_FROM_START);
			mPullRefreshListView.setRefreshing(false);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_category);
		initData();
		initView();

	}

	private void initView() {
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		back = (ImageView) findViewById(R.id.new_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView) findViewById(R.id.new_title);
		title.setText(_title);
		actualListView = mPullRefreshListView.getRefreshableView();
		adapter = new CategoryAdapter(appContext);
		actualListView.setAdapter(adapter);
		actualListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				LzList item = (LzList) adapter.getItem(position - 1);
				String _id = item.getID();// 文件夹的id
				String _itemType = item.getItemType();
				String _title = item.getTitle();
				ContextBean bean = item.getContext();
				String _formType = null;
				if (bean != null) {
					_formType = bean.getFormType();
				}
				if ("Folder".equals(_itemType)) {// 文件夹
					Intent i = new Intent(context, Category.class);
					i.putExtra(Constant._Title, _title);
					i.putExtra(Constant._Parent, _id);// 需要
					i.putExtra(Constant._Category, _category);// 需要
					startActivityForResult(i, JUMP_TO_CATEGORY);

				} else if ("Item".equals(_itemType)) {// 文件

					if ("Document".equals(_category)
							|| "Report".equals(_category)) {
						startActivityForResult(
								new Intent(context, OnLineWatch.class)
										.putExtra(Constant._Online_Url,
												item.getReadUrl())
										.putExtra(Constant._Title,
												item.getTitle())
										.putExtra(Constant._Category, _category),
								JUMP_TO_ONLINE);
						new ReadAsyncTask(context).execute(user.getAddress(),
								_id, user.getUsername(), user.getPwd());
					}

					else if ("WorkItem".equals(_category)) {
						startActivityForResult(
								new Intent(context, ManageActivity2.class)
										.putExtra(Constant._Category, _category)
										.putExtra(Constant._ID, _id)
										.putExtra(Constant._Title, _title)
										.putExtra(Constant._FormType, _formType)
										.putExtra(Constant._Parent, _parent),
								JUMP_TO_MANAGER);
					} else if ("ReportCenter".equals(_category)
							|| "Report".equals(_category)
							|| "ODocs".equals(_category)
							|| "News".equals(_category)) {

						if (item.getReadUrl() != null
								&& item.getReadUrl().indexOf(".pdf") != -1) {
							Toast.makeText(context, "不支持打开PDF格式",
									Toast.LENGTH_SHORT).show();
						} else {
							startActivityForResult(
									new Intent(context, OnLineWatch.class)
											.putExtra(Constant._Online_Url,
													item.getReadUrl())
											.putExtra(Constant._Title,
													item.getTitle())
											.putExtra(Constant._Category,
													_category), JUMP_TO_ONLINE);
							new ReadAsyncTask(context).execute(
									user.getAddress(), _id, user.getUsername(),
									user.getPwd());
						}

					}

				}
			}

		});

		mPullRefreshListView
				.setScrollingWhileRefreshingEnabled(!mPullRefreshListView
						.isScrollingWhileRefreshingEnabled());
		if ("Report".equals(_category)) {
			mPullRefreshListView.setMode(Mode.DISABLED);
		} else {
			mPullRefreshListView.setMode(Mode.BOTH);
		}
		mPullRefreshListView
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(// 下拉刷新
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						pageid = 1;
						state = PULL_DOWN;
						isRefresh = true;
						isRequestNet = true;
						new CategoryTask(appContext, context, handler,
								isRefresh).execute(user, _category,
								_parent == null ? "" : _parent, pageid,
								isRequestNet, "oa");
					}

					@Override
					public void onPullUpToRefresh(// 上拉更多
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						state = PULL_UP;
						isRefresh = true;
						isRequestNet = false;
						new CategoryTask(appContext, context, handler,
								isRefresh).execute(user, _category,
								_parent == null ? "" : _parent, (++pageid),
								isRequestNet, "oa");
					}

				});
	}

	private void initData() {
		// TODO Auto-generated method stub
		appContext = (AppContext) getApplication();
		context = this;
		user = appContext.initLoginInfo();
		Intent intent = getIntent();
		_parent = intent.getStringExtra(Constant._Parent);// 第2层需要
		_title = intent.getStringExtra(Constant._Title);
		_category = intent.getStringExtra(Constant._Category);// 需要
		Log.i(tag, "_category:" + _category);
		isRequestNet = true;
		new CategoryTask(appContext, context, handler, isRefresh).execute(user,
				_category, _parent == null ? "" : _parent, pageid,
				isRequestNet, "oa");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(RESULT_OK);
		}
		return super.onKeyDown(keyCode, event);
	}
}
