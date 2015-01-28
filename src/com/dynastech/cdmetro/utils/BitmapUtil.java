package com.dynastech.cdmetro.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapUtil {
	public final static int MAX_DEM = 960;
	private final static String TAG = "BitmapUtil";
	private final static float scaleWidth = 1, scaleHeight = 1;

	public static String codeBitmap2Base64(Bitmap pic, int quality)
			throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String encodeStr;
		int width = pic.getWidth();
		int height = pic.getHeight();
		int maxDime = Math.max(width, height);
		pic.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		byte[] temp = baos.toByteArray();
		encodeStr = new String(Base64.encodeBytes(baos.toByteArray()));
		baos.close();
		pic.recycle();
		return encodeStr;
	}

	public static Bitmap decodeBitmapFromBase64(String encodeStr)
			throws Exception {
		byte[] data = Base64.decode(encodeStr);
		return BitmapFactory.decodeByteArray(data, 0, data.length);
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
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
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

	public static Bitmap readBitmap(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		if (options.mCancel || options.outWidth == -1
				|| options.outHeight == -1) {
			return null;
		}
		options.inSampleSize = computeSampleSize(options, 600,
				(int) (0.5 * 1024 * 1024));
		options.inJustDecodeBounds = false;
		options.inDither = false;
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		return bitmap;
	}

	public static Bitmap scaleBitmap(Bitmap bmp, double scale) {
		// 获得Bitmap的高和宽
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		float scaleW = (float) (scaleWidth * scale);
		float scaleH = (float) (scaleHeight * scale);
		// 产生resize后的Bitmap对象
		Matrix matrix = new Matrix();
		matrix.postScale(scaleW, scaleH);
		bmp = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
		return bmp;
	}

	public static Bitmap decodeYUV420SP(byte[] rgbBuf, byte[] yuv420sp,
			int width, int height) {
		final int frameSize = width * height;
		if (rgbBuf == null)
			throw new NullPointerException("buffer 'rgbBuf' is null");
		if (rgbBuf.length < frameSize * 3)
			throw new IllegalArgumentException("buffer 'rgbBuf' size "
					+ rgbBuf.length + " < minimum " + frameSize * 3);

		if (yuv420sp == null)
			throw new NullPointerException("buffer 'yuv420sp' is null");

		if (yuv420sp.length < frameSize * 3 / 2)
			throw new IllegalArgumentException("buffer 'yuv420sp' size "
					+ yuv420sp.length + " < minimum " + frameSize * 3 / 2);

		int i = 0, y = 0;
		int uvp = 0, u = 0, v = 0;
		int y1192 = 0, r = 0, g = 0, b = 0;

		for (int j = 0, yp = 0; j < height; j++) {
			uvp = frameSize + (j >> 1) * width;
			u = 0;
			v = 0;
			for (i = 0; i < width; i++, yp++) {
				y = (0xff & ((int) yuv420sp[yp])) - 16;
				if (y < 0)
					y = 0;
				if ((i & 1) == 0) {
					v = (0xff & yuv420sp[uvp++]) - 128;
					u = (0xff & yuv420sp[uvp++]) - 128;
				}

				y1192 = 1192 * y;
				r = (y1192 + 1634 * v);
				g = (y1192 - 833 * v - 400 * u);
				b = (y1192 + 2066 * u);

				if (r < 0)
					r = 0;
				else if (r > 262143)
					r = 262143;
				if (g < 0)
					g = 0;
				else if (g > 262143)
					g = 262143;
				if (b < 0)
					b = 0;
				else if (b > 262143)
					b = 262143;

				rgbBuf[yp * 3] = (byte) (r >> 10);
				rgbBuf[yp * 3 + 1] = (byte) (g >> 10);
				rgbBuf[yp * 3 + 2] = (byte) (b >> 10);
			}
		}
		return BitmapFactory.decodeByteArray(rgbBuf, 0, rgbBuf.length);
	}

	public static void saveMyBitmap(Bitmap mBitmap) throws IOException {
		File f = new File("/mnt/sdcard/回调.png");
		f.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (mBitmap != null)
			mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
