/*
 * Copyright (C) 2013 yixia.com
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

package io.vov.vitamio.demo;

import java.net.URLDecoder;
import java.net.URLEncoder;

import com.dynastech.oa.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnErrorListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoViewDemo extends Activity implements OnClickListener {

	/**
	 * TODO: Set the path variable to a streaming video URL or a local media
	 * file path.
	 */
	private String path;
	private VideoView mVideoView;
	private TextView title;
	private ImageView back;
	private ProgressBar progressbar;
	private String tag = "VideoViewDemo";

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		try {
			setContentView(R.layout.videoview);
			mVideoView = (VideoView) findViewById(R.id.surface_view);
			progressbar = (ProgressBar) findViewById(R.id.progressBar1);
			title = (TextView) findViewById(R.id.new_title);
			back = (ImageView) findViewById(R.id.new_back);
			title.setText(getIntent().getStringExtra("audio_name"));
			path = getIntent().getStringExtra("path");
			if(path==null)
				return;
			path = URLDecoder.decode(path, "utf-8");
			path=path.substring(0,path.lastIndexOf("/")+1)+URLEncoder.
					encode(path.substring(path.lastIndexOf("/")+1), "utf-8");
			if(path.contains("+")){
				path=path.replaceAll("\\+","%20");
			}
			Log.i(tag, "path:"+path);
			back.setOnClickListener(this);
			mVideoView.setVideoPath(path);
			mVideoView.setMediaController(new MediaController(this));
			mVideoView.requestFocus();
			mVideoView
					.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
						@Override
						public void onPrepared(MediaPlayer mediaPlayer) {
							// optional need Vitamio 4.0
							progressbar.setVisibility(View.GONE);
							mediaPlayer.setPlaybackSpeed(1.0f);
						}
						
					});
			mVideoView.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub
					progressbar.setVisibility(View.GONE);
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}
}
