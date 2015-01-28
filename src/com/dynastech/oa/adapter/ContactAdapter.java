package com.dynastech.oa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

import com.dynastech.oa.R;
import com.dynastech.oa.entity.ContactFolder;
import com.dynastech.oa.entity.ContactUser;

public class ContactAdapter extends BaseAdapter {
	public static final int NORMAL = 0;
	public static final int PULL_DOWN = 1;
	public static final int PULL_UP = 2;
	private Context context;
	private LinkedList<Object> list_Depart_User;

	public ContactAdapter(Context paramContext) {
		this.context = paramContext;
	}

	public int getCount() {
		if (this.list_Depart_User != null)
			return this.list_Depart_User.size();
		return 0;
	}

	public Object getItem(int paramInt) {
		return this.list_Depart_User.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		ViewHolder localViewHolder;
		Object localObject;
		if (paramView == null) {
			localViewHolder = new ViewHolder();
			paramView = LayoutInflater.from(this.context).inflate(
					R.layout.contact_list_item, null);
			localViewHolder.icon = ((ImageView) paramView
					.findViewById(R.id.contact_item_icon));
			localViewHolder.title = ((TextView) paramView
					.findViewById(R.id.contact_item_title));
			localViewHolder.content = ((TextView) paramView
					.findViewById(R.id.contact_item_vicetitle));
			paramView.setTag(localViewHolder);

		} else {
			localViewHolder = (ViewHolder) paramView.getTag();
		}
		localObject = this.list_Depart_User.get(paramInt);
		if (localObject instanceof ContactFolder) {
			ContactFolder localContactFolder = (ContactFolder) localObject;
			localViewHolder.icon.setBackgroundResource(R.drawable.folder);
			localViewHolder.title.setText(localContactFolder.getName());
			localViewHolder.content.setText(localContactFolder.getPath());
		} else if (localObject instanceof ContactUser) {
			ContactUser localContactUser = (ContactUser) localObject;
			localViewHolder.icon.setBackgroundResource(R.drawable.text);
			localViewHolder.title.setText(localContactUser.getName());
			localViewHolder.content.setText(localContactUser.getMobilePhone());
		}

		return paramView;
	}

	public void setData(LinkedList<Object> paramLinkedList, int paramInt) {
		switch (paramInt) {
		default:
			return;
		case 0:
			this.list_Depart_User = paramLinkedList;
			return;
		case 1:
			this.list_Depart_User = paramLinkedList;
			return;
		case 2:
		}
		this.list_Depart_User.addAll(paramLinkedList);
	}

	class ViewHolder {
		TextView content;
		ImageView icon;
		TextView title;

		ViewHolder() {
		}
	}
}
