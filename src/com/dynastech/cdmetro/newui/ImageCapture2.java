package com.dynastech.cdmetro.newui;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import uk.co.senab.photoview.PhotoViewAttacher;

import com.dynastech.oa.R;
import com.dynastech.cdmetro.newui.common.Constant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class ImageCapture2 extends Activity implements OnClickListener,Runnable{

	private ImageView mImageView,new_back;
	private Bitmap bitmap;
	private Button save;
	private TextView new_title;
	private PhotoViewAttacher mAttacher;
	private ProgressBar progressbar;
	private String mCurrentPhotoPath;
	private Context mContext;
	private ProgressDialog mpDialog;
	private boolean isVisible;
	private Handler handler=new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_image_capture);
		mContext=this;
		mCurrentPhotoPath=getIntent().getStringExtra(Constant._TAKE_PHOTO_PATH);
		isVisible=getIntent().getBooleanExtra(Constant._IsVisible, false);
	
		save=(Button) findViewById(R.id.new_file_save);
		if(isVisible){
			save.setVisibility(View.VISIBLE);
		}
		save.setOnClickListener(this);
		mpDialog = new ProgressDialog(mContext);
		mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
		mpDialog.setIndeterminate(false);// 设置进度条是否为不明确
		mpDialog.setMessage("图片保存中...");
		
		new_back=(ImageView) findViewById(R.id.new_back);
		new_back.setOnClickListener(this);
		new_title=(TextView) findViewById(R.id.new_title);
		new_title.setText(getIntent().getStringExtra(Constant._Title)+"");
		mImageView = (ImageView) findViewById(R.id.capture_image);
		mAttacher = new PhotoViewAttacher(mImageView);	
		
		progressbar=(ProgressBar) findViewById(R.id.progressBar1);
		handler.postDelayed(this, 1000);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (mCurrentPhotoPath != null) {
			bitmap=getOriginalBitMap();
			mImageView.setImageBitmap(bitmap);
		    mAttacher.setScaleType(ScaleType.CENTER_CROP);
			mAttacher.update();
			galleryAddPic();
			mImageView.setVisibility(View.VISIBLE);
		    progressbar.setVisibility(View.GONE);
			
		}
		
	}
	public Bitmap getOriginalBitMap(){
		BitmapFactory.Options options = new BitmapFactory.Options();
        // 先设置为TRUE不加载到内存中，但可以得到宽和高
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, options); // 此时返回bm为空
        options.inJustDecodeBounds = false;
        // 计算缩放比
        int be = (int) (options.outHeight / (float) 400);
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;
        // 这样就不会内存溢出了
        return BitmapFactory.decodeFile(mCurrentPhotoPath, options);
	}
	private void galleryAddPic() {
	    Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(mCurrentPhotoPath);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==save){
			new ImageCompressTask().execute(bitmap);
		}else if(v==new_back){
			finish();
		}
	}
	private void setPic() {
	    // Get the dimensions of the View
	    int targetW = mImageView.getWidth();
	    int targetH = mImageView.getHeight();
	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;

	    int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
		}
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    bitmap= BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
	    mImageView.setImageBitmap(bitmap);
		mAttacher.update();
		mImageView.setVisibility(View.VISIBLE);
	    progressbar.setVisibility(View.GONE);
	}
	class ImageCompressTask extends AsyncTask<Bitmap, Void, Void>{

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
		try{
		File uploadFile=new File(mCurrentPhotoPath);
		 os = new BufferedOutputStream
				(new FileOutputStream(uploadFile));
        bitmap.compress(CompressFormat.JPEG, 50, os);
		}catch (IOException e) {
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
