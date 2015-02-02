package com.dynastech.oa.ui;

import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.dynastech.oa.AppContext;
import com.dynastech.oa.R;
import com.dynastech.oa.beans.ContextBean;
import com.dynastech.oa.beans.LzList;
import com.dynastech.oa.ui.adapter.CategoryAdapter;
import com.dynastech.oa.ui.adapter.ContactAdapter;
import com.dynastech.oa.ui.common.Constant;
import com.dynastech.oa.ui.entity.ContactData;
import com.dynastech.oa.ui.entity.ContactFolder;
import com.dynastech.oa.ui.entity.ContactUser;
import com.dynastech.oa.ui.entity.User;
import com.dynastech.oa.ui.tasks.CategoryTask;
import com.dynastech.oa.ui.tasks.ContactAsyncTask;
import com.dynastech.oa.ui.tasks.ReadAsyncTask;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

public class ContactListActivity extends BaseActivity {

	private PullToRefreshListView mPullRefreshListView;
	private AppContext appContext;
	private Context context;
	private ImageView back;
	private TextView title;
	private User user;
	private boolean isRefresh;
	private int pageid = 1;
	private int state;
	private String _id;
	private String _type;
	public static final int LIMIT = 20;
	public static final int NORMAL = 0;
	public static final int PULL_DOWN = 1;
	public static final int PULL_UP = 2;
	public static final int SUCCESS = 1;
	public static final int FAILED = -1;
	public static final int ERROR = 0;
	private ContactAdapter adapter;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				ContactData mContactData = (ContactData) msg.obj;
				LinkedList<Object> data_sets = mContactData
						.getContact_Depart_Users();
				if (data_sets != null && data_sets.size() != 0) {
					adapter.setData(data_sets, NORMAL);
					adapter.notifyDataSetChanged();
				}
			} else {
				Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
			}
		};
	};

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.new_category);
		initData();
		initView();
		new ContactAsyncTask(appContext, context, handler, isRefresh).execute(
				_type, _id, user.getUsername(), user.getPwd());
	}

	private void initData() {
		// TODO Auto-generated method stub
		appContext = (AppContext) getApplication();
		context = this;
		user = appContext.initLoginInfo();
		Intent intent = getIntent();
		_id = intent.getStringExtra("_id");
		_type = intent.getStringExtra("_type");

		
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
		title.setText("通讯录");
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		adapter = new ContactAdapter(context);
		actualListView.setAdapter(adapter);
		actualListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Object localObject = ContactListActivity.this.adapter
						.getItem(position - 1);
				if ((localObject instanceof ContactFolder)) {
					ContactFolder localContactFolder = (ContactFolder) localObject;
					ContactListActivity.this._id = localContactFolder.getId();
					ContactListActivity.this.startActivity(new Intent(
							ContactListActivity.this.context, ContactListActivity.class)
							.putExtra("_id", ContactListActivity.this._id));
				} else if (localObject instanceof ContactUser) {
					ContactUser localContactUser = (ContactUser) localObject;
					ContactListActivity.this._id = localContactUser.getId();
					String str = localContactUser.getMobilePhone();
					if ((str != null) && (!"".equals(str))) {
						Intent localIntent = new Intent(
								"android.intent.action.DIAL", Uri.parse("tel:"
										+ str));
						ContactListActivity.this.context.startActivity(localIntent);
						return;
					}
					Toast.makeText(ContactListActivity.this.context, "没有电话可拨打", 0)
							.show();
				}

			}

		});

		mPullRefreshListView.setMode(Mode.DISABLED);

	}
}
