package com.dynastech.oa.utils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

import com.dynastech.oa.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapCache {

	private static BitmapCache cache;// 一个Cache实例
	private Hashtable<String, BitmapCacheRef> bitmapRefs;// 用于Chche内容的存储
	private ReferenceQueue<Bitmap> q;// 垃圾Reference的队列

	static class BitmapCacheRef extends SoftReference<Bitmap> {

		String _key;

		public BitmapCacheRef(Bitmap bm) {
			super(bm);
			_key = bm.toString();
		}

	}

	private BitmapCache() {
		bitmapRefs = new Hashtable<String, BitmapCacheRef>();
		q = new ReferenceQueue<Bitmap>();
	}

	// 取得缓存器实例
	public static BitmapCache getInstance() {
		if (cache == null) {
			cache = new BitmapCache();
		}
		return cache;
	}

	// 以软引用的方式对一个Employee对象的实例进行引用并保存该引用
	private void cacheBitmap(Bitmap bm) {
		cleanCache();// 清除垃圾引用
		BitmapCacheRef ref = new BitmapCacheRef(bm);
		bitmapRefs.put(bm.toString(), ref);
	}

	// 依据所指定的ID号，重新获取相应Employee对象的实例
	public Bitmap getBitmap(String ID, Context context) {
		Bitmap em = null;
		// 缓存中是否有该Employee实例的软引用，如果有，从软引用中取得。
		if (bitmapRefs.containsKey(ID)) {
			BitmapCacheRef ref = (BitmapCacheRef) bitmapRefs.get(ID);
			em = (Bitmap) ref.get();
		}
		// 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
		// 并保存对这个新建实例的软引用
		if (em == null) {
			em = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.ic_launcher);
			this.cacheBitmap(em);
		}
		return em;
	}

	private void cleanCache() {
		BitmapCacheRef ref = null;
		while ((ref = (BitmapCacheRef) q.poll()) != null) {
			bitmapRefs.remove(ref._key);
		}
	}

	// 清除Cache内的全部内容
	public void clearCache() {
		cleanCache();
		bitmapRefs.clear();
		System.gc();
		System.runFinalization();

	}
}
