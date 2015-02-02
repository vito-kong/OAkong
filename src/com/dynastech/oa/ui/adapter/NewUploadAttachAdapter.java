package com.dynastech.oa.ui.adapter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.dynastech.oa.R;
import com.dynastech.oa.beans.AttachmentsBean;
import com.dynastech.oa.ui.entity.Attachment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NewUploadAttachAdapter extends BaseAdapter {

	private List<Attachment> attaches;
	private Context ctx;
	
	public NewUploadAttachAdapter(Context context) {
		// TODO Auto-generated constructor stub
		ctx=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return attaches==null?0:attaches.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return attaches.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		try {
		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(ctx).
					inflate(R.layout.upload_attach_item,null);
			holder.item_name=(TextView) convertView.
					findViewById(R.id.upload_item_name);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
			holder.item_name.setText(URLDecoder.decode(attaches.get(position).getFileName()+"","utf-8"));
		} catch (UnsupportedEncodingException e) {
	
			e.printStackTrace();
		}

		return convertView;
	}
	class ViewHolder {
		TextView item_name,item_size;
		
	}
	public void setData(List<Attachment> upload_attaches) {
		// TODO Auto-generated method stub
		attaches=upload_attaches;
	}
	

}
