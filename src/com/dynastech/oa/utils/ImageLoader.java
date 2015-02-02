package com.dynastech.oa.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;



import com.dynastech.oa.utils.ImageSDCardCache.OnImageSDCallListener;
import com.trinea.java.common.ObjectUtils;
import com.trinea.java.common.StringUtils;

public class ImageLoader {
	public static ImageLoader loader;
	// private OnImageSDCallListener callListener = new OnImageSDCallListener()
	// {
	//
	// private static final long serialVersionUID = 1L;
	//
	// @Override
	// public void onImageLoaded(String imageUrl, String imagePath, View view) {
	// if (!StringUtils.isEmpty(imagePath) && view != null) {
	// Drawable d = Drawable.createFromPath(imagePath);
	// if (d != null) {
	// ((ImageView)view).setImageDrawable(d);
	// }
	// }
	// }
	// };
	OnImageSDCallListener callListener = new OnImageSDCallListener() {

		private static final long serialVersionUID = 1L;

		@Override
		public void onImageLoaded(String imageUrl, String imagePath, View view) {
			if ((view.getTag() == null || ObjectUtils.isEquals(
					(String) view.getTag(), imageUrl))
					&& !StringUtils.isEmpty(imagePath) && view != null) {
				Drawable d = Drawable.createFromPath(imagePath);
				if (d != null) {
					((ImageView) view).setImageDrawable(d);
				}
			}
		}
	};
	// 定义图片缓存
	public ImageSDCardCache imageCache = new ImageSDCardCache(callListener, 256);

	public static ImageLoader getInstance() {

		if (loader == null) {
			loader = new ImageLoader();
		}

		return loader;

	}

	public void loadPic(String imageUrl, View imageView) {
		imageCache.loadImageFile(imageUrl, imageView);
	}

}
