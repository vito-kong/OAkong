/*
 * Copyright (C) 2012 Lightbox
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dynastech.oa.utils;

import java.io.IOException;

import android.app.Activity;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

/** 
 * MediaUtils 
 * @author Nilesh Patel
 */
public class MediaUtils {
	/** Used to tag logs */
	@SuppressWarnings("unused")
	private static final String TAG = "MediaUtils";
	
	public static String getPath(Activity activity, Uri uri) {
	    String[] projection = { MediaStore.Images.Media.DATA };
	    Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
	    activity.startManagingCursor(cursor);
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);
		
	}
	
	public static String getVideoPath(Activity activity, Uri uri){
	    String[] projection = { MediaStore.Video.Media.DATA };
	    Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
	    activity.startManagingCursor(cursor);
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);
		
	}
	public static String geVideotName(Activity activity, Uri uri) throws Exception{
		

	    String[] projection = { MediaStore.Video.Media.DATA };
	    Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
	    activity.startManagingCursor(cursor);
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
	    cursor.moveToFirst();
	    Uri filePathUri = Uri.parse(cursor.getString(column_index));
        String file_name = filePathUri.getLastPathSegment().toString();
        return file_name;
	}
	
	public static String getName(Activity activity, Uri uri) {
		

	    String[] projection = { MediaStore.Images.Media.DATA };
	    Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
	    activity.startManagingCursor(cursor);
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    Uri filePathUri = Uri.parse(cursor.getString(column_index));
        String file_name = filePathUri.getLastPathSegment().toString();
        return file_name;
	}
	
	public static int getExifOrientation(String filepath) {
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				// We only recognise a subset of orientation tag values.
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				}

			}
		}
		return degree;
	}
}
