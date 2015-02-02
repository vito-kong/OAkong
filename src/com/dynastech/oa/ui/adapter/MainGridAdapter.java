package com.dynastech.oa.ui.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dynastech.oa.R;
import com.dynastech.oa.beans.Data;

public class MainGridAdapter extends BaseAdapter{

	private int[] images = { R.drawable.sub_daiban, R.drawable.sub_message,
			R.drawable.sub_chart, R.drawable.sub_task, R.drawable.sub_data_c,
			R.drawable.sub_notification ,R.drawable.sub_daiban, R.drawable.sub_data_c,
			R.drawable.sub_chart};
	private LinkedList<Data> d = new LinkedList<Data>();
	private Context context;
	
	public MainGridAdapter(Context ctx) {
		// TODO Auto-generated constructor stub
		context=ctx;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return d.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return d.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setData(LinkedList<Data> data) {
		d = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder item;

		if (convertView == null) {
			item = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.mainpage_gridview,
					parent, false);

			item.background = (RelativeLayout) convertView;
			item.icon = (ImageView) convertView
					.findViewById(R.id.lz_mainpage_icon);

			item.newmsg = (TextView) convertView
					.findViewById(R.id.lz_mainpage_newmsg);
			item.title = (TextView) convertView
					.findViewById(R.id.lz_mainpage_text);
			convertView.setTag(item);
		} else {
			item = (ViewHolder) convertView.getTag();
		}

		item.background.setBackgroundResource(images[position]);

		String unread = d.get(position).getUnReadCount();

		if ("0".equals(unread)) {
			item.newmsg.setVisibility(View.INVISIBLE);
		} else {
			item.newmsg.setVisibility(View.VISIBLE);
			item.newmsg.setText(unread);
		}
		item.title.setText(d.get(position).getText());

		return convertView;
	}

	class ViewHolder {
		ImageView icon;
		TextView newmsg;
		TextView title;
		RelativeLayout background;
	}
}
