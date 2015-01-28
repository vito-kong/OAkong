package com.dynastech.cdmetro.utils;

import java.io.File;

import android.os.Environment;

public class Util {
	public static String NEWMSG_BROADCAST = "com.diipo.weibo.newmsg";
	public static String UPDATE_BROADCAST = "com.diipo.weibo.update";

	public static String DOWNLOAD_PATH = Environment
			.getExternalStorageDirectory() + "/diipo/download/";
	public static String savePath = Environment.getExternalStorageDirectory()
			+ "/diipo/image/";
	public static String TEMP_DIR = Environment.getExternalStorageDirectory()
			+ "/diipo/temp/";
	public static String PHOTO_TEMP = Environment.getExternalStorageDirectory()
			+ "/diipo/temp/phpto_temp.jpg";
	public static String UPLOAD_PIC_Temp = Environment
			.getExternalStorageDirectory() + "/diipo/temp/uppic_temp.jpg";
	public static String UPLOAD_THUMB_Temp = Environment
			.getExternalStorageDirectory() + "/diipo/temp/upthumb_temp.jpg";

	public static void print(String msg) {
		System.out.println(msg);
	}

	public static void loge(String error) {
		// Log.e("diipo", error);
	}

	public static void makePicDir(String path) {
		String d = Environment.getExternalStorageDirectory().toString() + path;
		File dir = new File(d);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

}
