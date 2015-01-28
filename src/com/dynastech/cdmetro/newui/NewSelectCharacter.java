package com.dynastech.cdmetro.newui;

import java.util.LinkedList;
import java.util.List;

import com.dynastech.oa.R;
import com.dynastech.cdmetro.beans.UserBean;
import com.dynastech.cdmetro.beans.UsersBean;
import com.dynastech.cdmetro.newui.adapter.NewSelectUserAdapter;
import com.dynastech.cdmetro.newui.common.Constant;
import com.dynastech.cdmetro.newui.entity.GroupUserBean;
import com.dynastech.cdmetro.newui.util.JsonUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NewSelectCharacter extends BaseActivity implements OnClickListener{

	private Button save;
	private ImageView back;
	private TextView title;
	private ListView selectuser_list;
	private Context context;
	private String title_text;
	private List<UsersBean> result;
	private LinkedList<UsersBean> result_selected;
	private LinkedList<GroupUserBean> 
			gubeans=new LinkedList<GroupUserBean>();
	private NewSelectUserAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_selectuser);
		context=this;
		Intent intent = getIntent();
		back = (ImageView) findViewById(R.id.new_back);
		save = (Button) findViewById(R.id.new_file_save);
		save.setVisibility(View.VISIBLE);
		title = (TextView) findViewById(R.id.new_title);
		selectuser_list = (ListView) findViewById(R.id.new_selectuser_list);
		title_text = intent.getStringExtra(Constant._Title);
		title.setText(title_text + "");
		
		result=(List<UsersBean>) 
				intent.getSerializableExtra(Constant._Select_Users);
		result_selected=new LinkedList<UsersBean>();
		
		save.setOnClickListener(this);
		back.setOnClickListener(this);
		
		for(UsersBean usersBean:result){
			
			UsersBean new_usersbean=new UsersBean();
			new_usersbean.setActGuid(usersBean.getActGuid());
			new_usersbean.setApprovalCount(usersBean.getApprovalCount());
			new_usersbean.setApproverType(usersBean.getApproverType());
			new_usersbean.setName(usersBean.getName());
			new_usersbean.setResultMode(usersBean.getResultMode());
			result_selected.add(new_usersbean);
			
			
			GroupUserBean gbean = new GroupUserBean();
			gbean.setChecked(false);
			gbean.setGroup(true);
			gbean.setGroup_user_name(usersBean.getName());
			gubeans.add(gbean);
			
			LinkedList<UserBean> users = usersBean.getUsers();
			if (users != null && users.size() != 0) {
				for (UserBean user : users) {
					GroupUserBean ubean = new GroupUserBean();
					ubean.setChecked(false);
					ubean.setGroup(false);
					ubean.setGroup_user_name(user.getUserName());
					ubean.setLogin_name(user.getLoginName());
					ubean.setUser(user);
					gubeans.add(ubean);
				}
			}
			
		}
		
		adapter=new NewSelectUserAdapter(context);
		selectuser_list.setAdapter(adapter);
		adapter.setData(gubeans);
		adapter.notifyDataSetChanged();
		
		
	}
	@Override
	public void onClick(View v) {
		if(v==back){
			finish();
		}else if(v==save){
			
			int count = 0,pos=0;
			LinkedList<UserBean> users= new LinkedList<UserBean>();
			for (int i=0;i<gubeans.size();i++) {
				if (gubeans.get(i).isGroup()&&i!=0) {
					result_selected.get(pos).setUsers(users);
					users.clear();
					pos++;
				} else if(gubeans.get(i).isChecked()){
					count++;
					users.add(gubeans.get(i).getUser());
				}
				
			}
			if(count==0){
				Toast.makeText(context, "至少选择一个角色", Toast.LENGTH_SHORT).show();
			}else{
				result_selected.get(pos).setUsers(users);
				setResult(RESULT_OK, new Intent().putExtra("NextApprovers",result_selected));
				finish();
				
			}
			
		}
		
	}
}
