package com.dynastech.oa.ui.adapter;

import java.util.LinkedList;
import java.util.List;

import com.dynastech.oa.R;
import com.dynastech.oa.beans.UsersBean;
import com.dynastech.oa.ui.adapter.NewBranchAdapter.ViewHolder;
import com.dynastech.oa.ui.entity.GroupUserBean;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class NewSelectUserAdapter extends BaseAdapter {

	LinkedList<GroupUserBean> groupUserBeans;
	Context ctx;
	
	public void setData(LinkedList<GroupUserBean> groupUsers){
		groupUserBeans=groupUsers;
	}
	
	public NewSelectUserAdapter(Context context) {
		// TODO Auto-generated constructor stub
		ctx=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return groupUserBeans==null?0:groupUserBeans.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return groupUserBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
//		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(ctx).inflate(R.layout.select_user_item, null);
			holder.checkbox=(CheckBox) convertView.findViewById(R.id.branch_checked);
			holder.textView=(TextView) convertView.findViewById(R.id.branch_name);
//			convertView.setTag(holder);
//		}else{
//			holder = (ViewHolder) convertView.getTag();
//		}
		
		if(groupUserBeans.get(position).isGroup()){
			holder.checkbox.setVisibility(View.GONE);
			holder.textView.setBackgroundColor(Color.GRAY);
		}else{
			holder.checkbox.setVisibility(View.VISIBLE);
			holder.textView.setBackgroundColor(Color.WHITE);
		}
		
		holder.checkbox
		.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				
				groupUserBeans.get(position).setChecked(isChecked);

			}
		});
		holder.checkbox.setChecked(groupUserBeans.get(position).isChecked());
		holder.textView.setText(groupUserBeans.get(position).getGroup_user_name());
		

		return convertView;
	}

	class ViewHolder {
		CheckBox checkbox;
		TextView textView;
	}

}
