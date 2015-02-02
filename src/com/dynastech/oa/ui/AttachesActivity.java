package com.dynastech.oa.ui;

import io.vov.vitamio.demo.VideoViewDemo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dynastech.oa.AppContext;
import com.dynastech.oa.R;
import com.dynastech.oa.ui.adapter.NewUploadAttachAdapter;
import com.dynastech.oa.ui.common.Constant;
import com.dynastech.oa.ui.entity.Attachment;
import com.dynastech.oa.ui.entity.User;
import com.dynastech.oa.ui.util.ToolUtils;
import com.dynastech.oa.utils.HttpUtils;
import com.dynastech.oa.utils.URLS;
import com.u1aryz.android.lib.newpopupmenu.MenuItem;
import com.u1aryz.android.lib.newpopupmenu.PopupMenu;
import com.u1aryz.android.lib.newpopupmenu.PopupMenu.OnItemSelectedListener;

public class AttachesActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener, OnItemSelectedListener {

	private List<Attachment> attaches;
	private ImageView back;
	private TextView title;
	private ListView attach_list;
	private NewUploadAttachAdapter adapter;
	private String _title,user,pwd,domain_delete,_id,_WorkflowId;
	private final static int PLAY_SELECTION = 0;
	private final static int DELETE_SELECTION = 1;
	private Context context;
	private String tag="AttachesActivity";
	private AlertDialog dialog;
	private int pos;
	private User _user;
	private AppContext appContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_attach_list);
		context = this;
		appContext = (AppContext) getApplication();
		_user = appContext.initLoginInfo();
		Intent intent = getIntent();
		attaches = (List<Attachment>) intent
				.getSerializableExtra(Constant._Attach);
		_title = intent.getStringExtra(Constant._Title);
		_id=intent.getStringExtra(Constant._ID);
		_WorkflowId=intent.getStringExtra(Constant._WorkflowId);
	
		domain_delete=_user.getFileServerHandlerUrl();
		user=_user.getUsername();
		pwd=_user.getPwd();
	
		back = (ImageView) findViewById(R.id.new_back);
		title = (TextView) findViewById(R.id.new_title);
		title.setText(_title + "");
		dialog=new AlertDialog.Builder(this).create();
		back.setOnClickListener(this);
		attach_list = (ListView) findViewById(R.id.new_attach_list);
		attach_list.setOnItemClickListener(this);
		adapter = new NewUploadAttachAdapter(this);
		attach_list.setAdapter(adapter);
		adapter.setData(attaches);
		adapter.notifyDataSetChanged();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		pos=position;
		PopupMenu menu = new PopupMenu(this);
		menu.setHeaderTitle(attaches.get(position).getFileName());
		menu.setFilename(attaches.get(position).getFileName());
		menu.setUrl(attaches.get(position).getUrl());
		menu.setOnItemSelectedListener(this);

		menu.add(PLAY_SELECTION, R.string.attachs_play).setIcon(
				getResources().getDrawable(
						R.drawable.ic_context_menu_play_normal));
		menu.add(DELETE_SELECTION, R.string.attachs_delete).setIcon(
				getResources().getDrawable(
						R.drawable.ic_context_menu_search_normal));
		menu.show(view);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == back) {
			setResult(RESULT_OK);
			finish();
		}
	}

	@Override
	public void onItemSelected(MenuItem item) {

		String filename = item.getPopmenu().getFilename();
		String url = item.getPopmenu().getUrl();
		String extension="";
		if(filename!=null&&!"".equals(filename)&&filename.indexOf(".")!=-1){
			extension=filename.substring(filename.lastIndexOf("."));
		}
		
		switch (item.getItemId()) {

		case PLAY_SELECTION:// 查看
			 if (ToolUtils.isAudioAllowRead(extension)) {
				// 在线打开音频
				disIntentVitamioActivity(url,filename);
				
			} else if (ToolUtils.isVideoAllowRead(extension)) {
				// 在线打开视频
				disIntentVitamioActivity(url,filename);
				
			} else if (ToolUtils.isImageAllowRead(extension)) {
				// 在线打开图片
				disIntentImageActivity(url, filename);
			
			} else {
				Toast.makeText(context, "不支持打开该格式的附件", Toast.LENGTH_SHORT).show();
			}
			break;
		
		case DELETE_SELECTION:// 删除
			try {
				new DeleteTask().execute(URLEncoder.encode(filename,"utf-8"),
					_id.substring(_id.lastIndexOf("_")+1),_WorkflowId,pos+"");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

	}

	private void disIntentVitamioActivity(String path,String filename) {
		// TODO Auto-generated method stub
		context.startActivity(new Intent(context,
				VideoViewDemo.class).
				putExtra("path",path).
				putExtra("audio_name",filename));
	}
	
	private void disIntentImageActivity(String path,String filename) {
		// TODO Auto-generated method stub
		startActivity(new Intent(context, OnlineImage.class)
		.putExtra(Constant._Online_Url,path)
		.putExtra(Constant._Title, filename));
	}

	public boolean attachFileIsExit
		(String filename, String foldername)  {

		File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),foldername);;
		makeDir(storageDir);
		File file=new File(storageDir.getPath() + File.separator+filename);
		return file.exists();

	}
	public void makeDir(File mediaStorageDir) {
		if (!mediaStorageDir.exists()) {
			mediaStorageDir.mkdirs();
		}

	}
	
	class DeleteTask extends AsyncTask<String, Void, String> {

		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (!((Activity) context).isFinishing()) {
				dialog.show();
				Window window = dialog.getWindow();
				window.setContentView(R.layout.progress);
				TextView progressText = (TextView) window
						.findViewById(R.id.progress_text);
				progressText.setText("附件删除中...");
			}
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try{
			StringBuffer url = new StringBuffer();
			url.append(domain_delete);
			url.append(URLS._Attach_Delete);
			url.append("&name="+params[0]);
			url.append("&taskId="+params[1]);
			url.append("&workflowId="+params[2]);
			Log.i(tag, "url:" + url.toString());
			return  HttpUtils._get_basic(url.toString(), user, pwd);
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.i(tag, "result:" +result);
			dialog.dismiss();
			attaches.remove(pos);
			adapter.notifyDataSetChanged();
		}
		
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			setResult(RESULT_OK);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}

}
