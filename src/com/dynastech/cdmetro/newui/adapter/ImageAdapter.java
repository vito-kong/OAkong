package com.dynastech.cdmetro.newui.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.dynastech.oa.R;

public class ImageAdapter extends BaseAdapter {
	private static final int ITEM_WIDTH = 90;
	private static final int ITEM_HEIGHT = 90;

	private  int mGalleryItemBackground;
	private Context mContext;

	private final Integer[] mImageIds = { R.drawable.camera_i_filter_dazzle,
			R.drawable.camera_i_filter_diana, R.drawable.camera_i_filter_fu_gu,
			R.drawable.camera_i_filter_hei_bai, R.drawable.camera_i_filter_hui_yi,
			R.drawable.camera_i_filter_lake, R.drawable.camera_i_filter_liu_nian,
			R.drawable.camera_i_filter_lomo10};

	private final float mDensity;

	public ImageAdapter(Context c) {
		mContext = c;
		// See res/values/attrs.xml for the <declare-styleable> that defines
		// Gallery1.
		TypedArray a = mContext.obtainStyledAttributes(R.styleable.Gallery1);
		mGalleryItemBackground = a.getResourceId(
				R.styleable.Gallery1_android_galleryItemBackground, 0);
		a.recycle();

		mDensity = mContext.getResources().getDisplayMetrics().density;
	}

	public int getCount() {
		return mImageIds.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			convertView = new ImageView(mContext);

			imageView = (ImageView) convertView;
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setLayoutParams(new Gallery.LayoutParams(
					(int) (ITEM_WIDTH * mDensity + 0.5f),
					(int) (ITEM_HEIGHT * mDensity + 0.5f)));

			// The preferred Gallery item background
			imageView.setBackgroundResource(mGalleryItemBackground);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(mImageIds[position]);

		return imageView;
	}
}
