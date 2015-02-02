package com.dynastech.oa.ui;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.LinkedList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dynastech.oa.AppContext;
import com.dynastech.oa.R;
import com.dynastech.oa.beans.UserBean;
import com.dynastech.oa.beans.UsersBean;
import com.dynastech.oa.ui.adapter.NewBranchCharacterAdapter;
import com.dynastech.oa.ui.common.Constant;
import com.dynastech.oa.ui.entity.GroupUserBean;
import com.dynastech.oa.ui.entity.User;
import com.dynastech.oa.ui.util.JsonUtils;
import com.dynastech.oa.utils.HttpUtils;
import com.dynastech.oa.utils.URLS;
import com.google.gson.reflect.TypeToken;

public class NewBranchCharacter extends BaseActivity implements OnClickListener {

	private Button save;
	private ImageView back;
	private TextView title;
	private ListView branch_character_list;
	private String title_text, approvalBy, domain, user, pwd,approvalBy_Users;
	private AlertDialog dialog;
	private Context context;
	private String tag = "NewBranchCharacter";
	private NewBranchCharacterAdapter adapter;
	private LinkedList<GroupUserBean> groupUserBeans = new LinkedList<GroupUserBean>();
	private StringBuffer buffer = new StringBuffer();
	private StringBuffer bufferUsers = new StringBuffer();
	private User _user;
	private AppContext appContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			context = this;
			appContext = (AppContext) getApplication();
			_user = appContext.initLoginInfo();
			setContentView(R.layout.new_branch_character);
			Intent intent = getIntent();
			back = (ImageView) findViewById(R.id.new_back);
			save = (Button) findViewById(R.id.new_file_save);
			save.setVisibility(View.VISIBLE);
			title = (TextView) findViewById(R.id.new_title);
			branch_character_list = (ListView) findViewById(R.id.new_branch_character_list);
			title_text = intent.getStringExtra(Constant._Title);
			title.setText(title_text + "");
			approvalBy = intent.getStringExtra(Constant._ApprovalBy);
			approvalBy_Users=intent.getStringExtra(Constant._ApprovalBy_USERS);
			dialog = new AlertDialog.Builder(this).create();
			back.setOnClickListener(this);
			save.setOnClickListener(this);
			domain=_user.getAddress();
			user=_user.getUsername();
			pwd=_user.getPwd();
			adapter = new NewBranchCharacterAdapter(this);
			branch_character_list.setAdapter(adapter);

			new NewBranchApprovalTask().execute(URLEncoder.encode(approvalBy,
					"utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	class NewBranchApprovalTask extends AsyncTask<String, Void, String> {

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
				progressText.setText("获取数据中...");
			}
		}

		@Override
		protected String doInBackground(String... params) {

			// TODO Auto-generated method stub
			String result = null;
			try {
				StringBuffer url = new StringBuffer();
				url.append(domain);
				url.append(URLS._BrachCharacter + "&approvalBy="
						+ params[0]);
				Log.i(tag, "curl:" + url.toString());
				result = HttpUtils._get_ntml(url.toString(), user, pwd,context);
				Log.i(tag, "cresult:" + result);
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
				Type listType = new TypeToken<LinkedList<UsersBean>>() {
				}.getType();
				LinkedList<UsersBean> UsersBeans = JsonUtils
						.parseUserFromJsons(result, listType);

				for (UsersBean usersBean : UsersBeans) {
					GroupUserBean gbean = new GroupUserBean();
					gbean.setChecked(false);
					gbean.setGroup(true);
					gbean.setGroup_user_name(usersBean.getName());
					groupUserBeans.add(gbean);
					LinkedList<UserBean> users = usersBean.getUsers();
					if (users != null && users.size() != 0) {
						for (UserBean user : users) {
							GroupUserBean ubean = new GroupUserBean();
							if(approvalBy_Users!=null&&approvalBy_Users.contains(user.getUserName())){
								ubean.setChecked(true);
							}else{
								ubean.setChecked(false);
							}
							
							ubean.setGroup(false);
							ubean.setGroup_user_name(user.getUserName());
							ubean.setLogin_name(user.getLoginName());
							groupUserBeans.add(ubean);
						}
					}

				}
				adapter.setData(groupUserBeans);
				adapter.notifyDataSetChanged();

			} else {
				Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	public void onClick(View v) {
		if (v == back) {
			setResult(RESULT_CANCELED);
			finish();
		} else if (v == save) {
			for (GroupUserBean gubean : groupUserBeans) {
				if (gubean.isChecked()) {
					String loginName = gubean.getLogin_name();
					String userName = gubean.getGroup_user_name();
					String combine = userName + "|" + loginName;
					buffer.append(combine + ";");
					bufferUsers.append(userName+";");
				}
			}
			if (buffer.toString().length() != 0) {
				setResult(
						RESULT_OK,
						new Intent().putExtra(Constant._ApprovalBy,
								buffer.toString()).
								putExtra(Constant._ApprovalBy_USERS, bufferUsers.toString()));
				finish();
			} else {
				Toast.makeText(context, "至少选择一个角色", Toast.LENGTH_SHORT).show();
			}
		}

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 返回键未保存
			setResult(RESULT_CANCELED);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
