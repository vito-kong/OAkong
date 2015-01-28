package com.dynastech.cdmetro.newui.adapter;

import io.vov.vitamio.demo.VideoViewDemo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;

import com.dynastech.cdmetro.AppContext;
import com.dynastech.oa.R;
import com.dynastech.cdmetro.beans.AttachmentsBean;
import com.dynastech.cdmetro.newui.ImageCapture;
import com.dynastech.cdmetro.newui.OnLineWatch;
import com.dynastech.cdmetro.newui.common.Constant;
import com.dynastech.cdmetro.newui.entity.Downloader;
import com.dynastech.cdmetro.newui.util.FileUtils;
import com.dynastech.cdmetro.newui.util.ToolUtils;
import com.dynastech.cdmetro.utils.CallOtherOpeanFile;
import com.dynastech.cdmetro.utils.HttpUtils;
import com.dynastech.cdmetro.utils.NetUtils;
import com.dynastech.cdmetro.utils.URLS;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class NewAttachAdapter extends BaseAdapter {

	private LinkedList<AttachmentsBean> beans;
	private Context context;
	private AppContext appContext;
	private String download_localpath, user, pwd;
	private Handler handler;
	private String tag = "NewAttachAdapter";

	public NewAttachAdapter(Context ctx, Handler handler, String user,
			String pwd, AppContext appContext) {

		download_localpath = Constant._AttachFragment_Path;
		context = ctx;
		this.handler = handler;
		this.user = user;
		this.pwd = pwd;
		this.appContext = appContext;
	}

	public void setData(LinkedList<AttachmentsBean> beans) {
		this.beans = beans;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return beans == null ? 0 : beans.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.attach_list_item, null);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.attach_icon);
			holder.seeonline = (Button) convertView
					.findViewById(R.id.attach_seeonline);
			holder.download = (Button) convertView
					.findViewById(R.id.attach_download);

			holder.title = (TextView) convertView
					.findViewById(R.id.attach_title);
			holder.size = (TextView) convertView.findViewById(R.id.attach_size);
			holder.time = (TextView) convertView.findViewById(R.id.attach_time);
			holder.bar = (ProgressBar) convertView
					.findViewById(R.id.attachment_progressbar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		int size = beans.get(position).getSize();
		holder.bar.setMax((int) (size * 1024));

		holder.icon.setBackgroundResource(getImageResId(beans.get(position)
				.getSuffix()));
		holder.seeonline.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				context.startActivity(new Intent(context, OnLineWatch.class)
						.putExtra(Constant._Online_Url, beans.get(position).getUrl())
						.putExtra(Constant._Title, beans.get(position).getFilename()));

			}
		});
		String url_attach = URLS.ATTACHMENT_DOWNLOAD
				+ beans.get(position).getUrl();
		try {
			url_attach = url_attach.substring(0, url_attach.lastIndexOf("/") + 1)
					+ URLEncoder.encode(
							url_attach.substring(url_attach.lastIndexOf("/") + 1),
							"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(url_attach.contains("+")){
			url_attach=url_attach.replaceAll("\\+","%20");
		}
		holder.bar
				.setProgress(appContext.getDownloaders().get(url_attach) == null ? 0
						: (int) appContext.getDownloaders().get(url_attach)
								.getProgress());

		File folder = new File(download_localpath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String state = FileUtils.isFileExit(download_localpath + File.separator
				+ beans.get(position).getFilename());

		final String extension = beans.get(position).getSuffix();

		if (state.equals("finished")) {// open

			holder.bar.setVisibility(View.GONE);
			holder.download.setText("打开");
			holder.download.setBackgroundResource(R.drawable.attach_clickable);
			holder.download.setClickable(true);
			holder.download.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (ToolUtils.isOfficeAllowRead(extension)) {

						CallOtherOpeanFile.openFile(context, new File(
								download_localpath + File.separator
										+ beans.get(position).getFilename()));
					} else if (ToolUtils.isAudioAllowRead(extension)
							|| ToolUtils.isVideoAllowRead(extension)) {
						context.startActivity(new Intent(context,
								VideoViewDemo.class).putExtra(
								"path",
								download_localpath + File.separator
										+ beans.get(position).getFilename())
								.putExtra("audio_name",
										beans.get(position).getFilename()));
					} else if (ToolUtils.isImageAllowRead(extension)) {
						String filepath = download_localpath + File.separator
								+ beans.get(position).getFilename();
						File file=new File(filepath);
						if(!file.exists())
							return;
						context.startActivity(new Intent(context, ImageCapture.class)
						.putExtra(Constant._IMAGE_URI,Uri.fromFile(file))
						.putExtra(Constant._Title,  beans.get(position).getFilename())
						.putExtra(Constant._IsVisible, false)
						.putExtra(Constant._TAKE_PHOTO_PATH, filepath));
						
					} else {
						Toast.makeText(context, "不支持打开该格式", Toast.LENGTH_SHORT)
								.show();
					}

				}
			});

		} else if (state.equals("downloading")) {

			holder.download.setText("下载中");
			holder.download.setBackgroundResource(R.drawable.attach_clicked);
			holder.download.setClickable(false);
			holder.bar.setVisibility(View.VISIBLE);

		} else if (state.equals("init")) {
			holder.bar.setVisibility(View.GONE);
			holder.download.setText("下载");
			holder.download.setBackgroundResource(R.drawable.attach_clickable);
			holder.download.setClickable(true);
			holder.download.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (!NetUtils.isNetworkAvailable(context)) {
						Toast.makeText(context, "网络未连接", Toast.LENGTH_SHORT)
								.show();
						return;
					}

					if (!hasSdcard()) {
						Toast.makeText(context, "sd卡未挂载", Toast.LENGTH_SHORT)
								.show();
						return;
					}

					holder.download.setText("下载中");
					holder.download
							.setBackgroundResource(R.drawable.attach_clicked);
					Toast.makeText(context, "开始下载", Toast.LENGTH_SHORT).show();
					holder.download.setClickable(false);
					Downloader downloader = new Downloader();
					downloader.setFilesize((long) ((beans.get(position)
							.getSize()) * 1024));
					holder.bar.setVisibility(View.VISIBLE);
					String url_attach = URLS.ATTACHMENT_DOWNLOAD
							+ beans.get(position).getUrl();
					try {
						url_attach = url_attach.substring(0, url_attach.lastIndexOf("/") + 1)
								+ URLEncoder.encode(
										url_attach.substring(url_attach.lastIndexOf("/") + 1),
										"utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if(url_attach.contains("+")){
						url_attach=url_attach.replaceAll("\\+","%20");
					}
					appContext.getDownloaders().put(url_attach, downloader);

					if (android.os.Build.VERSION.SDK_INT > 10) {
						new AuthTask().executeOnExecutor(
								AsyncTask.THREAD_POOL_EXECUTOR, url_attach,
								download_localpath + File.separator
										+ beans.get(position).getFilename(),
								user, pwd);
					} else {
						new AuthTask().execute(url_attach, download_localpath
								+ File.separator
								+ beans.get(position).getFilename(), user, pwd);
					}

				}
			});
		}
		if (ToolUtils.isImageAllowRead(extension)
				|| ToolUtils.isAudioAllowRead(extension)
				|| ToolUtils.isVideoAllowRead(extension)
				|| ToolUtils.isImageAllowRead(extension)) {

			holder.seeonline.setTextColor(Color.parseColor("#080808"));
			holder.seeonline.setClickable(true);
			holder.seeonline
					.setBackgroundResource(R.drawable.ap_button_lightblue);
		} else {

			holder.seeonline.setClickable(false);
			holder.seeonline.setBackgroundResource(R.drawable.ap_button_silver);
			holder.seeonline.setTextColor(Color.parseColor("#DEDEDE"));

		}

		holder.title.setText(beans.get(position).getFilename() + "");
		holder.size.setText(beans.get(position).getSize() + "KB");

		return convertView;
	}

	private String getOkPath(String url) {
		// TODO Auto-generated method stub
		try {
			url=url.
					substring(0, url.lastIndexOf("/")+1)
			+URLEncoder.encode(url.substring(url.lastIndexOf("/")+1),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(url.contains("+")){
			url=url.replaceAll("\\+","%20");
		}
		return url;
	}

	class ViewHolder {
		ImageView icon;
		Button seeonline, download;
		TextView title, size, time, author;
		ProgressBar bar;

	}

	private int getImageResId(String suffix) {

		if (".xls".equals(suffix) || ".xlsx".equals(suffix)) {
			return R.drawable.excel;
		} else if (".doc".equals(suffix) || ".docx".equals(suffix)) {
			return R.drawable.word;
		} else if (".ppt".equals(suffix) || ".pptx".equals(suffix)) {
			return R.drawable.powerpoint;
		} else if (ToolUtils.isImageAllowRead(suffix)) {
			return R.drawable.image;
		} else if (ToolUtils.isAudioAllowRead(suffix)) {
			return R.drawable.audio;
		} else if (ToolUtils.isVideoAllowRead(suffix)) {
			return R.drawable.video;
		}
		return R.drawable.text;
	}

	public boolean hasSdcard() {
		String sd = Environment.getExternalStorageState();
		if (sd.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	class AuthTask extends AsyncTask<String, Void, String> implements Runnable {

		String url;
		String filepath;
		File temp;

		@Override
		protected String doInBackground(String... params) {

			url = params[0];
			filepath = params[1];
			temp = new File(filepath + ".temp");

			try {
				if (!temp.exists()) {
					temp.createNewFile();
				}
				handler.post(this);
				return HttpUtils._get_download_basic(params[0], params[1], appContext,
						params[2], params[3],context);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "failed";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			handler.removeCallbacks(this);
			appContext.getDownloaders().remove(url);

			if ("ok".equals(result)) {
				Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
				if (temp.exists()) {
					temp.delete();
				}
				File file = new File(filepath);
				if (file.exists()) {
					file.delete();
				}
			}
			notifyDataSetChanged();

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			notifyDataSetChanged();
			handler.postDelayed(this, 2000);
		}

	}

}
