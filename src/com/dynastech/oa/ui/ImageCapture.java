package com.dynastech.oa.ui;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.DisplayType;
import it.sephiroth.android.library.imagezoom.test.utils.DecodeUtils;

import com.dynastech.oa.R;
import com.dynastech.oa.ui.ImageCapture2.ImageCompressTask;
import com.dynastech.oa.ui.common.Constant;
import com.dynastech.oa.utils.MediaUtils;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ImageCapture extends BaseActivity implements OnClickListener {

	private Uri imageUri;
	private boolean isSaveVisible;
	private String filename, mCurrentFilePath;
	private ImageViewTouch mImage;
	private Matrix imageMatrix;
	private ImageView back;
	private TextView image_title;
	private Button save;
	private ProgressDialog mpDialog;
	private Bitmap bitmap;
	private String tag = "ImageCapture";
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_image_capture);
		imageUri = getIntent().getParcelableExtra(Constant._IMAGE_URI);
		isSaveVisible = getIntent().getBooleanExtra(Constant._IsVisible, true);
		filename = getIntent().getStringExtra(Constant._Title);
		mCurrentFilePath = getIntent()
				.getStringExtra(Constant._TAKE_PHOTO_PATH);
		if (imageUri == null)
			return;
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		mImage = (ImageViewTouch) findViewById(R.id.capture_image);
		back = (ImageView) findViewById(R.id.new_back);
		image_title = (TextView) findViewById(R.id.new_title);
		image_title.setText(filename + "");
		save = (Button) findViewById(R.id.new_file_save);
		if (isSaveVisible)
			save.setVisibility(View.VISIBLE);
		save.setOnClickListener(this);
		back.setOnClickListener(this);
		mpDialog = new ProgressDialog(this);
		mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
		mpDialog.setIndeterminate(false);// 设置进度条是否为不明确
		mpDialog.setMessage("图片保存中...");
		mImage.setDisplayType(DisplayType.FIT_IF_BIGGER);
		int size = -1; 
		// use the original image size
		bitmap = DecodeUtils.decode(this, imageUri, 3072, 3072);
		if (bitmap!=null) {
			// calling this will force the image to fit the ImageView
			// container width/height

			if (imageMatrix==null) {
				imageMatrix = new Matrix();
			} else {
				// get the current image matrix, if we want restore the
				// previous matrix once the bitmap is changed
				 imageMatrix = mImage.getDisplayMatrix();
			}

			mImage.setImageBitmap(bitmap, imageMatrix.isIdentity() ? null
					: imageMatrix, ImageViewTouchBase.ZOOM_INVALID,
					ImageViewTouchBase.ZOOM_INVALID);
			
		} else {
			Toast.makeText(this, R.string.load_failed_img, Toast.LENGTH_LONG).show();
		}
		progressBar.setVisibility(View.GONE);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == back) {
			finish();
		} else if (v == save) {
			new ImageCompressTask().execute(bitmap);
		}
	}

	class ImageCompressTask extends AsyncTask<Bitmap, Void, Void> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mpDialog.show();
		}

		@Override
		protected Void doInBackground(Bitmap... params) {
			// TODO Auto-generated method stub
			compressImage(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mpDialog.dismiss();
			setResult(RESULT_OK);
			finish();
		}

	}

	private void compressImage(Bitmap image) {

		OutputStream os = null;
		try {

			File uploadFile = new File(mCurrentFilePath);
			os = new BufferedOutputStream(new FileOutputStream(uploadFile));
			bitmap.compress(CompressFormat.JPEG, 50, os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		bitmap.recycle();

	}
}
