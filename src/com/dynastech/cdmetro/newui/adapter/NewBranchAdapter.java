package com.dynastech.cdmetro.newui.adapter;

import java.util.List;

import com.dynastech.oa.R;
import com.dynastech.cdmetro.beans.ActivityBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class NewBranchAdapter extends BaseAdapter {
	

	List<ActivityBean> branch_activities;
	Context ctx;
	public NewBranchAdapter(List<ActivityBean> activities,Context context) {
		// TODO Auto-generated constructor stub
		branch_activities=activities;
		ctx=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return branch_activities==null?0:branch_activities.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return branch_activities.get(position);
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
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(ctx).inflate(R.layout.branch_item, null);
			holder.checkbox=(CheckBox) convertView.findViewById(R.id.branch_checked);
			holder.textView=(TextView) convertView.findViewById(R.id.branch_name);
			holder.character_choosed=(TextView) convertView.findViewById(R.id.branch_character_choosed);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		holder.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				
				if (isChecked) {
					branch_activities.get(position).setChecked(true);
				} else {
					branch_activities.get(position).setChecked(false);
				}
			}
		});
		holder.checkbox.setChecked(branch_activities.get(position).isChecked());
		holder.textView.setText(branch_activities.get(position).getTag() + "");
		if(branch_activities.get(position).getApprovalBy_Users()==null||"".equalsIgnoreCase(branch_activities.get(position).getApprovalBy_Users())){
			holder.character_choosed.setVisibility(View.GONE);
		}else{
			holder.character_choosed.setVisibility(View.VISIBLE);
			holder.character_choosed.setText(branch_activities.get(position).getApprovalBy_Users()+"");
		}
		
		return convertView;
	}
	class ViewHolder {
		CheckBox checkbox;
		TextView textView,character_choosed;
	}

}
