package com.dynastech.oa.ui;

import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.dynastech.cdmetro.AppContext;
import com.dynastech.cdmetro.newui.BaseActivity;
import com.dynastech.cdmetro.newui.OnLineWatch;
import com.dynastech.cdmetro.newui.common.Constant;
import com.dynastech.cdmetro.newui.entity.User;
import com.dynastech.oa.R;
import com.dynastech.oa.adapter.ContactAdapter;
import com.dynastech.oa.adapter.DocumentsAdapter;
import com.dynastech.oa.entity.ContactData;
import com.dynastech.oa.entity.ContactFolder;
import com.dynastech.oa.entity.ContactUser;
import com.dynastech.oa.entity.DocumentsData;
import com.dynastech.oa.task.ContactAsyncTask;
import com.dynastech.oa.task.DocumentsTask;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;

public class DocumentsList extends BaseActivity {

	private PullToRefreshListView mPullRefreshListView;
	private AppContext appContext;
	private Context context;
	private ImageView back;
	private TextView title;
	private User user;
	private boolean isRefresh;
	public static final int LIMIT = 20;
	public static final int NORMAL = 0;
	public static final int PULL_DOWN = 1;
	public static final int PULL_UP = 2;
	public static final int SUCCESS = 1;
	public static final int FAILED = -1;
	public static final int ERROR = 0;
	private DocumentsAdapter adapter;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {

				LinkedList<DocumentsData> data_sets = (LinkedList<DocumentsData>) msg.obj;
				if (data_sets != null && data_sets.size() != 0) {
					adapter.setData(data_sets, NORMAL);
					adapter.notifyDataSetChanged();
				}
			} else {
				Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_category);
		initData();
		initView();
		new DocumentsTask(appContext, context, handler, isRefresh).execute(
				user.getUsername(), user.getPwd());
	}

	private void initData() {
		// TODO Auto-generated method stub
		appContext = (AppContext) getApplication();
		context = this;
		user = appContext.initLoginInfo();

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
		title.setText("文档库");
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		adapter = new DocumentsAdapter(context);
		actualListView.setAdapter(adapter);
		actualListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				DocumentsData localDocumentsData = (DocumentsData) DocumentsList.this.adapter
						.getItem(position - 1);
				String str = localDocumentsData.getmMetadata().getMedia_src();
				DocumentsList.this.startActivity(new Intent(
						DocumentsList.this.context, OnLineWatch.class)
						.putExtra(Constant._Online_Url, str).putExtra(
								Constant._Title,
								localDocumentsData.getFilename()).
								putExtra(Constant._Category,"Documents" ));

			}

		});

		mPullRefreshListView.setMode(Mode.DISABLED);

	}
}
