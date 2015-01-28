package com.dynastech.cdmetro.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;

public class FileUtilsDonggu {
	// private static String SDPATH;

	// public static String getSDPATH() {
	// return SDPATH;
	// }
	// static {
	// if (Environment.getExternalStorageState().equals(
	// Environment.MEDIA_MOUNTED))
	// SDPATH = Environment.getExternalStorageDirectory()
	// .getAbsolutePath() + File.separator;
	// }
	static String tag = "FileUtilsDonggu";

	// public FileUtils(){
	// 得到当前外部存储设备的目录( /SDCARD )
	// SDPATH = Environment.getExternalStorageDirectory() + "/donggu/";
	// createSDDir(SDPATH);
	// }

	/**
	 * 在SD卡上创建文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public File createSDFile(String fileName) throws IOException {

		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	public static File createSDFile(String dir, String fileName) {
		File file = new File(dir + File.separator + fileName);
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 * @return
	 */

	public static void createDir(String dir) {
		File d = new File(dir + File.separator);
		if (d.exists())
			return;
		d.mkdirs();

	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isFolderExist(String localFolder) throws Exception {
		File file = new File(localFolder);
		return file.exists();
	}

	public static boolean isFileExist(String filename) {
		File file = new File(filename);
		return file.exists();
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 * 
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */

	public static String isFileDownloaded(String filename, int contentlen) {

		Log.i(tag, "filename:" + filename);
		Log.i(tag, "len:" + contentlen);
		File f = new File(filename);

		if (!f.exists()) {
			return "init";
		}
		Log.i(tag, "f.length():" + f.length() );
		
		if (f.length()  < contentlen) {
			return "downloading";
		}

		if (f.length() == contentlen) {
			return "finished";
		}

		return "error";

	}
	public static String isFileExit(String filename){
		File f = new File(filename+".temp");
		File f_finished = new File(filename);
		
		if(f_finished.exists()){
			return "finished";
		}
		
		
		if (!f.exists()) {
			return "init";
		}else if(f.exists()){
			return "downloading";
		}
		
		
		return "error";
	}
	public static Bitmap readBitmapFormAbsFile(String filePath, Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int maxWidth = wm.getDefaultDisplay().getHeight();
		int maxHeight = wm.getDefaultDisplay().getWidth();
		String file = filePath;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(file, opts);

		opts.inSampleSize = BitmapUtil.computeSampleSize(opts, -1, maxWidth
				* maxHeight);
		opts.inJustDecodeBounds = false;
		int be = (int) (opts.outHeight / (float) maxWidth);
		if (be <= 0)
			be = 1;
		opts.inSampleSize = be;
		bmp = BitmapFactory.decodeFile(file, opts);
		return bmp;
	}

	public static Bitmap optimizeBitmap(byte[] resource, int maxWidth,
			int maxHeight) {
		Bitmap result = null;
		int length = resource.length;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		result = BitmapFactory.decodeByteArray(resource, 0, length, options);
		int widthRatio = (int) Math.ceil(options.outWidth / maxWidth);
		int heightRatio = (int) Math.ceil(options.outHeight / maxHeight);
		if (widthRatio > 1 || heightRatio > 1) {
			if (widthRatio > heightRatio) {
				options.inSampleSize = widthRatio;
			} else {
				options.inSampleSize = heightRatio;
			}
		}
		options.inJustDecodeBounds = false;
		result = BitmapFactory.decodeByteArray(resource, 0, length, options);
		return result;
	}

	public static Bitmap readBitmapFormAbsFile(String filePath, int maxWidth,
			int maxHeight) {
		Bitmap bmp = null;
		try {
			String file = filePath;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(file, opts);
			opts.inSampleSize = computeSampleSize(opts, -1, maxWidth
					* maxHeight);
			opts.inJustDecodeBounds = false;
			bmp = BitmapFactory.decodeFile(file, opts);
		} catch (Exception e) {
			if (bmp != null && !bmp.isRecycled()) {
				bmp.recycle();
			}
		}
		return bmp;

	}

	public static File writeToFileFormInputStream(String dir, String fileName,
			InputStream input) {
		createDir(dir);
		Log.i(tag, "dir:" + dir);
		File file = createSDFile(dir, fileName);
		Log.i(tag, "fileName:" + fileName);
		Log.i(tag, (file == null) + "");
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			int temp;
			while ((temp = input.read(buffer)) != -1)
				fileOutputStream.write(buffer, 0, temp);
			fileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
				if (input != null) {
					input.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return file;
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);
		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 1280 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}



	public static String getTypeByStream(InputStream is) {
		byte[] b = new byte[4];
		try {
			is.read(b, 0, b.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String type = bytesToHexString(b).toUpperCase();
		if (type.contains("FFD8FF")) {
			return "jpg";
		} else if (type.contains("89504E47")) {
			return "png";
		} else if (type.contains("47494638")) {
			return "gif";
		} else if (type.contains("49492A00")) {
			return "tif";
		} else if (type.contains("424D")) {
			return "bmp";
		}
		return type;
	}

	public static String getTypeByStream(byte[] data) {
		byte[] b = new byte[4];
		b[0] = data[0];
		b[1] = data[1];
		b[2] = data[2];
		b[3] = data[3];
		String type = bytesToHexString(b).toUpperCase();
		if (type.contains("FFD8FF")) {
			return "jpg";
		} else if (type.contains("89504E47")) {
			return "png";
		} else if (type.contains("47494638")) {
			return "gif";
		} else if (type.contains("49492A00")) {
			return "tif";
		} else if (type.contains("424D")) {
			return "bmp";
		}
		return type;
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
}
