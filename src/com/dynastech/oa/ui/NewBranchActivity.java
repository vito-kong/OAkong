package com.dynastech.oa.ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dynastech.oa.AppContext;
import com.dynastech.oa.R;
import com.dynastech.oa.beans.ActivityBean;
import com.dynastech.oa.beans.BranchBean;
import com.dynastech.oa.ui.adapter.NewBranchAdapter;
import com.dynastech.oa.ui.common.Constant;
import com.dynastech.oa.ui.entity.User;
import com.dynastech.oa.ui.util.JsonUtils;
import com.dynastech.oa.utils.HttpUtils;
import com.dynastech.oa.utils.URLS;

public class NewBranchActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener {

	private Button save;
	private ImageView back;
	private TextView title;
	private ListView branch_list;
	private BranchBean branchBean;
	private LinkedList<ActivityBean> activities;
	private NewBranchAdapter new_branch_adapter;
	private String title_text;
	public static final int REQUEST_BRANCH_CHARACTER = 1;
	private int sequence, branch_Count;
	private Context context;
	private String domain, user, pwd, WorkflowId;
	private AlertDialog dialog;
	private String tag = "NewBranchActivity";
	private String orignal_ApprovalBy,approvalBy_Users;
	private User _user;
	private AppContext appContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_branch);
		context = this;
		appContext = (AppContext) getApplication();
		_user = appContext.initLoginInfo();
		Intent intent = getIntent();
		back = (ImageView) findViewById(R.id.new_back);
		save = (Button) findViewById(R.id.new_file_save);
		save.setVisibility(View.VISIBLE);
		title = (TextView) findViewById(R.id.new_title);
		branch_list = (ListView) findViewById(R.id.new_branch_list);
		
		WorkflowId = intent.getStringExtra(Constant._WorkflowId);
		title_text = intent.getStringExtra(Constant._Title);
		branchBean = (BranchBean) intent.getSerializableExtra(Constant._Branch);
		activities = branchBean.getActivities();
		branch_Count = branchBean.getApprovalCount();
		domain=_user.getAddress();
		user=_user.getUsername();
		pwd=_user.getPwd();
		
		title.setText(title_text + "");
		back.setOnClickListener(this);
		save.setOnClickListener(this);
		branch_list.setOnItemClickListener(this);
		dialog = new AlertDialog.Builder(this).create();
		new_branch_adapter = new NewBranchAdapter(activities, this);
		branch_list.setAdapter(new_branch_adapter);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_BRANCH_CHARACTER) {
			if (resultCode == RESULT_OK) {
				String approvalBy = data.getStringExtra(Constant._ApprovalBy);
				approvalBy_Users= data.getStringExtra(Constant._ApprovalBy_USERS);
				activities.get(sequence).setApprovalBy_Users(approvalBy_Users);
//				activities.get(sequence).setApprovalBy(approvalBy);
				activities.get(sequence).setHasChooseUsers(true);
				activities.get(sequence).setAssignedTo(approvalBy);
			} else if (resultCode == RESULT_CANCELED) {
				approvalBy_Users="";
				activities.get(sequence).setApprovalBy_Users(null);
//				activities.get(sequence).setApprovalBy(orignal_ApprovalBy);
				activities.get(sequence).setHasChooseUsers(false);
				activities.get(sequence).setAssignedTo(orignal_ApprovalBy);
			}
			branchBean.setActivities(activities);
			new_branch_adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if (!activities.get(position).isHasChooseUsers()) {
			orignal_ApprovalBy = activities.get(position).getApprovalBy();
		}
		
		CheckBox box = (CheckBox) view.findViewById(R.id.branch_checked);
		
		if (box.isChecked()) {
			sequence = position;
			startActivityForResult(new Intent(this, NewBranchCharacter.class)
					.putExtra(Constant._ApprovalBy, orignal_ApprovalBy)
					.putExtra(Constant._Title, title_text).
					putExtra(Constant._ApprovalBy_USERS, approvalBy_Users),
					REQUEST_BRANCH_CHARACTER);
		} else {
			Toast.makeText(context, "请勾选复选框进入选人界面", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (back == v) {
			finish();
		} else if (save == v) {
			int count = 0;
			
			for (ActivityBean abean : activities) {
				if (abean.isChecked()) {
					if (!abean.isHasChooseUsers()) {
						Toast.makeText(context, "你有选中的分支没有选人",
								Toast.LENGTH_SHORT).show();
						return;
					}
					count++;
				} else {
					abean.setStatus(-3);
				}
			}

			if (branch_Count == 1) {
				if (count != 1) {
					Toast.makeText(context, "你必须且只能选1个分支", Toast.LENGTH_SHORT)
							.show();
				} else {

					new UpdateNextTask().execute();
				}
			} else {
				if (count != 0) {
					new UpdateNextTask().execute();
				} else {
					Toast.makeText(context, "你至少选1个分支", Toast.LENGTH_SHORT)
							.show();
				}
			}

		}
	}

	class UpdateNextTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (!((Activity) context).isFinishing()) {
				dialog.show();
				Window window = dialog.getWindow();
				window.setContentView(R.layout.progress);
				TextView progressText = (TextView) window
						.findViewById(R.id.progress_text);
				progressText.setText("数据提交中...");
			}
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			try {
				StringBuffer url = new StringBuffer();
				url.append(domain);
				url.append(URLS._BranchTask_Submit);
				Map<String, String> map = new HashMap<String, String>();
				map.put("workflowId", WorkflowId);
				Log.i(tag, "workflowId:"+WorkflowId);
				map.put("actInfo", JsonUtils.parseObejectToString(branchBean));
				Log.i(tag, "branchBean:"+JsonUtils.parseObejectToString(branchBean));
				result = HttpUtils._post_ntlm(url.toString(), user, pwd, map);
				Log.i(tag, "result:" + result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			if (result!=null&&!"".equals(result.trim())) {
				if ("1".equals(result)) {
					// 保存成功
					setResult(RESULT_OK);
					finish();

				} else if ("0".equals(result)) {
					// 保存失败
					Toast.makeText(context, "数据保存失败", Toast.LENGTH_SHORT)
							.show();
				}
			} else {
				Toast.makeText(context, "数据保存失败", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
