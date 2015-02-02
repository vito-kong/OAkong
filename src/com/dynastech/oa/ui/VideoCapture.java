package com.dynastech.oa.ui;

import com.dynastech.oa.R;
import com.dynastech.oa.ui.common.Constant;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoCapture extends BaseActivity implements OnClickListener,OnPreparedListener{

	private ImageView new_back;
	private Button save;
	private TextView new_title;
	private String _title;
	private VideoView videoView;
	private Uri mCurrentPhotoPath;
	private String tag="VideoCapture";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_video_capture);
		initData();
		initView();
	}

	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		mCurrentPhotoPath = intent.getParcelableExtra
				(Constant._TAKE_VIDEO_PATH);
		Log.i(tag, "mCurrentPhotoPath:"+mCurrentPhotoPath);
		_title=intent.getStringExtra(Constant._Title) ;
	}

	
	private void initView() {
		// TODO Auto-generated method stub
		videoView = (VideoView) findViewById(R.id.video_thumb);
		new_back = (ImageView) findViewById(R.id.new_back);
		save = (Button) findViewById(R.id.new_file_save);
		save.setVisibility(View.VISIBLE);
		new_title = (TextView) findViewById(R.id.new_title);
		new_title.setText(_title+"");
		new_back.setOnClickListener(this);
		save.setOnClickListener(this);
		
		videoView.setVideoURI(mCurrentPhotoPath);
		videoView.setMediaController(new MediaController(this));
		videoView.requestFocus();
		videoView.setOnPreparedListener(this);
		videoView.start();
		

	}

	@Override
	public void onClick(View v) {
		if(videoView.isPlaying()){
			videoView.pause();
			videoView.stopPlayback();
			}
		
		switch (v.getId()) {
		case R.id.new_back:
			finish();
			break;
		
		case R.id.new_file_save:
			setResult(RESULT_OK);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	}
}
