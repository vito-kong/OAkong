package com.dynastech.oa.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

import com.dynastech.oa.R;
import com.dynastech.oa.ui.entity.DocumentsData;

public class DocumentsAdapter extends BaseAdapter {
	public static final int NORMAL = 0;
	public static final int PULL_DOWN = 1;
	public static final int PULL_UP = 2;
	private Context context;
	private LinkedList<DocumentsData> mDocumentsDatas;

	public DocumentsAdapter(Context paramContext) {
		this.context = paramContext;
	}

	public int getCount() {
		if (this.mDocumentsDatas == null)
			return 0;
		return this.mDocumentsDatas.size();
	}

	public Object getItem(int paramInt) {
		return this.mDocumentsDatas.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		ViewHolder localViewHolder;
		if (paramView == null) {
			localViewHolder = new ViewHolder();
			paramView = LayoutInflater.from(this.context).inflate(R.layout.contact_list_item,
					null);
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
		DocumentsData localDocumentsData = (DocumentsData) this.mDocumentsDatas
				.get(paramInt);
		localViewHolder.icon
				.setBackgroundResource(officeDrawable(localDocumentsData
						.getFilename().substring(
								localDocumentsData.getFilename().lastIndexOf(
										"."))));
		localViewHolder.title.setText(localDocumentsData.getFilename());
		localViewHolder.content.setText(localDocumentsData.getModify_time());

		return paramView;

	}

	public int officeDrawable(String paramString) {
		if ((".doc".equals(paramString)) || (".docx".equals(paramString)))
			return R.drawable.word;
		if ((".xls".equals(paramString)) || (".xlsx".equals(paramString)))
			return R.drawable.excel;
		if ((".pptx".equals(paramString)) || (".ppt".equals(paramString)))
			return R.drawable.powerpoint;
		return R.drawable.text;
	}

	public void setData(LinkedList<DocumentsData> paramLinkedList, int paramInt) {
		switch (paramInt) {
		default:
			return;
		case 0:
			this.mDocumentsDatas = paramLinkedList;
			return;
		case 1:
			this.mDocumentsDatas = paramLinkedList;
			return;
		case 2:
		}
		this.mDocumentsDatas.addAll(paramLinkedList);
	}

	class ViewHolder {
		TextView content;
		ImageView icon;
		TextView title;

	}
}
