package com.dynastech.oa.ui.adapter;

import java.util.LinkedList;

import com.dynastech.oa.AppContext;
import com.dynastech.oa.R;
import com.dynastech.oa.beans.ContextBean;
import com.dynastech.oa.beans.LzList;
import com.dynastech.oa.utils.AsyncImageLoader.ImageCallback;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {

	private LinkedList<LzList> dt = new LinkedList<LzList>();
	private AppContext appContext;
	public static final int NORMAL = 0;
	public static final int PULL_DOWN = 1;
	public static final int PULL_UP = 2;
	private Animation anim;
	public CategoryAdapter(AppContext appctx) {
		// TODO Auto-generated constructor stub
		appContext = appctx;
		anim=AnimationUtils.loadAnimation(appctx, R.anim.list_anim);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dt.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dt.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder item;
		if (convertView == null) {
			item = new ViewHolder();
			convertView = LayoutInflater.from(appContext).inflate(
					R.layout.categoty_list, null);
			item.icon = (ImageView) convertView
					.findViewById(R.id.category_item_icon);
			item.iconProgress = (ProgressBar) convertView
					.findViewById(R.id.category_item_iconprogress);
			item.title = (TextView) convertView
					.findViewById(R.id.categoty_item_title);
			item.viceTitle = (TextView) convertView
					.findViewById(R.id.categoty_item_vicetitle);
			item.folderAdd = (TextView) convertView
					.findViewById(R.id.categoty_item_add);
			item.fileNew = (TextView) convertView
					.findViewById(R.id.categoty_item_new);
			convertView.setTag(item);

		} else {
			item = (ViewHolder) convertView.getTag();
		}
		Log.i("CategoryAdapter", dt.get(position).getIconUrl());
		Drawable drawable = appContext.getAsyncImageLoader().loadDrawable(
				appContext, dt.get(position).getIconUrl(), new ImageCallback() {

					@Override
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						Log.i("CategoryAdapter", "imageLoaded");
						if(imageDrawable!=null)
						notifyDataSetChanged();
					}
					
				});
		

		if (drawable == null) {
			item.icon.setBackgroundResource(R.drawable.common_defaultimage_bg);
		} else {
			item.icon.setBackgroundDrawable(drawable);
		}
		
		item.title.setText(dt.get(position).getTitle() + "");
		item.viceTitle.setText(dt.get(position).getViceTitle() + "");
		String type = dt.get(position).getItemType();

		if ("Folder".equals(type)) {// 文件夹
			boolean isParent = dt.get(position).isIsParent();
			if (isParent) {// 有2级目录
				int unreadCount = dt.get(position).getUnReadCount();
				if (unreadCount != 0) {
					item.folderAdd.setVisibility(View.VISIBLE);
					item.folderAdd.setText(unreadCount + "");
				} else {
					item.folderAdd.setVisibility(View.GONE);
				}
			}

		} else if ("Item".equals(type)) {// 文件
			boolean isNew = dt.get(position).isIsUnRead();
			if (isNew) {// 未看过
				item.fileNew.setVisibility(View.VISIBLE);
				item.folderAdd.setVisibility(View.GONE);

			} else {
				item.fileNew.setVisibility(View.GONE);
				item.folderAdd.setVisibility(View.GONE);

			}

		}

		return convertView;
	}

	private int getImageResId(String formType) {
		if ("Folder".equals(formType)) {
			return R.drawable.folder;
		} else if ("Excel".equals(formType)) {
			return R.drawable.excel;
		} else if ("Page".equals(formType)) {
			return R.drawable.page;
		} else if ("InfoPath".equals(formType)) {
			return R.drawable.infopath;
		} else if ("OfficialDoc".equals(formType)) {
			return R.drawable.officialdoc;
		} else if ("PowerPoint".equals(formType)) {
			return R.drawable.powerpoint;
		} else if ("Text".equals(formType)) {
			return R.drawable.text;
		} else if ("WebPage".equals(formType)) {
			return R.drawable.webpage;
		} else if ("Word".equals(formType)) {
			return R.drawable.word;
		} else if ("Item".equals(formType)) {
			return R.drawable.items;
		}
		return R.drawable.text;
	}

	class ViewHolder {
		ImageView icon;
		TextView title, viceTitle, folderAdd, fileNew;
		ProgressBar iconProgress;
	}

	public void setData(LinkedList<LzList> result, int status) {

		switch (status) {
		case NORMAL:// 第一次请求数据
			dt = result;
			break;
		case PULL_DOWN:// 下拉刷新
			dt = result;
			break;
		case PULL_UP:// 上拉更多
			dt.addAll(result);
			break;
		default:
			break;
		}

	}

}
