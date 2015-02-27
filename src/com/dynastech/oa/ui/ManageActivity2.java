package com.dynastech.oa.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dynastech.oa.AppContext;
import com.dynastech.oa.R;
import com.dynastech.oa.beans.ApprovalBean;
import com.dynastech.oa.beans.Args;
import com.dynastech.oa.beans.BranchBean;
import com.dynastech.oa.beans.ManageBean;
import com.dynastech.oa.beans.RuleBean;
import com.dynastech.oa.beans.UsersBean;
import com.dynastech.oa.beans.UsersBeanCopy;
import com.dynastech.oa.ui.adapter.NewApprovalAdapter;
import com.dynastech.oa.ui.adapter.NewAttachAdapter;
import com.dynastech.oa.ui.common.Constant;
import com.dynastech.oa.ui.entity.Attachment;
import com.dynastech.oa.ui.entity.PermissionBean;
import com.dynastech.oa.ui.entity.User;
import com.dynastech.oa.utils.FileUtils;
import com.dynastech.oa.utils.FormType;
import com.dynastech.oa.utils.HttpUtils;
import com.dynastech.oa.utils.ImageUtils;
import com.dynastech.oa.utils.JsonUtils;
import com.dynastech.oa.utils.MediaUtils;
import com.dynastech.oa.utils.NetWorkHelper;
import com.dynastech.oa.utils.StringUtils;
import com.dynastech.oa.utils.UIHelper;
import com.dynastech.oa.utils.URLS;
import com.google.gson.reflect.TypeToken;

public class ManageActivity2 extends BaseActivity implements OnClickListener,
		OnTouchListener, OnGestureListener {

	private ImageView back;
	private TextView manager_title, form_title, form_origantor;
	private Button new_form, new_text, new_attachment, new_approval, new_todo,
			alert_cancel, alert_ok;
	private WebView webform, webtext;
	private ListView attach_list, newapproval_list;
	private LinearLayout layout_content;
	private Button tool_camera, tool_video, tool_picture, button_control,
			button_audio, recorder_upload, recorder_delete;
	private TextView tool_attachment, reject, terminal, revocation, approval,
			no_approval, time_record, recorder_title, alert_prompt,
			alert_result;
	private EditText layout_content_edit;
	private String _id, _parent, _title, _formtype, _category, permission,
			WorkflowId, itemType, formlib, filename, itemContent,
			mCurrentFilePath;
	private Context context;
	private String domain, user, pwd, upload_url, mFileName, newState, Result;
	private AlertDialog dialog, dialog_record, dialog_deal,
			dialog_record_upload, dialog_warning;
	private String tag = "ManageActivity2";
	private ManageBean managebean;
	private NewAttachAdapter attachadapter;
	private NewApprovalAdapter approvaladapter;
	private Handler handler = new Handler();
	private File protraitFile;
	private Uri origUri, cropUri;
	private final static int CROP_WIDTH = 800;
	private final static int CROP_HEIGHT = 800;
	public static final int REQUEST_TAKE_PHOTO = 1;
	public static final int REQUEST_GET_PHOTO = 2;
	public static final int REQUEST_TAKE_VIDEO = 3;
	public static final int REQUEST_TAKE_AUDIO = 4;
	public static final int RESULT_CHARACTER = 5;
	public static final int SEELECT_CHARACTER = 6;
	public static final int REQUEST_TAKE_PHOTO_SAVE = 7;
	public static final int REQUEST_GET_PHOTO_SAVE = 8;
	public static final int REQUEST_TAKE_VIDEO_SAVE = 9;
	public static final int REQUEST_TAKE_AUDIO_SAVE = 10;
	public static final int RESULT_ATTACHMENT = 11;
	public static final int MIN_INTERVAL_TIME = 1000;
	public static final String Reject_Code = "1";
	public static final String Terminal_Code = "3";
	public static final String Revocation_Code = "4";
	public static final String Approval_Code = "9";
	private boolean isRuleOk, isPlaying, canUploadFile, isSelectUser;
	private ImageButton recorder_play;
	private int time;
	private long startTime;
	private boolean isFirstNode;
	private MediaRecorder mRecorder;
	private MediaPlayer mPlayer;
	private View alert_dialog;
	private Args args;
	private ProgressDialog mpDialog;
	private LinkedList<Attachment> new_attachments;
	private User _user;
	private AppContext appContext;
	private GestureDetector mygesture;
	private static final int FLING_MIN_DISTANCE = 120;// 移动最小距离
	private static final int FLING_MIN_VELOCITY = 200;// 移动最大速度
	private float scale_init;
	private Runnable run = new Runnable() {

		@Override
		public void run() {

			time++;
			if (time < 10) {
				time_record.setText("00:0" + time + "/00:30");
			} else if (time >= 10 && time <= 30) {
				time_record.setText("00:" + time + "/00:30");
			} else if (time > 30) {
				time = 30;
				button_audio.setBackgroundResource(R.drawable.dotool_audio);
				handler.removeCallbacks(this);
				dialog_record.dismiss();
				try {
					stopRecording();
				} catch (Exception e) {
					e.printStackTrace();
				}
				alert_dialog_recorder();
				return;
			}

			handler.postDelayed(this, 1000);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_layout_manager);
		initData();
		initView();
		initNetSendMessage();
	}

	private void stopPlaying() {
		isPlaying = false;
		if (mPlayer != null) {
			mPlayer.reset();
		}
		if (recorder_play != null)
			recorder_play.setBackgroundResource(R.drawable.video_play_btn_nor);
	}

	private void startPlaying() {
		isPlaying = true;

		try {
			recorder_play
					.setBackgroundResource(R.drawable.btn_alert_stop_normal);
			mPlayer.setDataSource(mCurrentFilePath);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void alert_dialog_recorder() {
		try {

			dialog_record_upload.show();
			Window win = dialog_record_upload.getWindow();
			win.setContentView(R.layout.dialog_recorder_upload);

			recorder_title = (TextView) win.findViewById(R.id.recorder_title);
			recorder_play = (ImageButton) win.findViewById(R.id.recorder_play);
			recorder_upload = (Button) win.findViewById(R.id.recorder_upload);
			recorder_delete = (Button) win.findViewById(R.id.recorder_delete);
			recorder_title.setText("你录了" + time + "秒的音频,点击播放");
			recorder_play.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 播放录音
					if (isPlaying) {
						stopPlaying();
					} else {
						startPlaying();
					}

				}
			});
			recorder_upload.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						stopPlaying();
						attachMethod(mCurrentFilePath);
						dialog_record_upload.dismiss();

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			recorder_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					stopPlaying();
					// 删除录音
					File audio = new File(mCurrentFilePath);
					if (audio.exists() && audio.isFile()) {
						audio.delete();
					}
					dialog_record_upload.dismiss();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stopRecording() throws Exception {

		if (mRecorder != null) {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
		}

	}

	/** Create a File for saving an image or video */
	private Uri getOutputMediaFile() {

		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(Constant._UPLOAD_Attach_Path_Videos);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			UIHelper.ToastMessage(context, R.string.save_video_failed_to_no_sdcard);
			return null;
		}
		File mediaFile = null;
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		// 视频命名
		String cropFileName = "metro_video_" + timeStamp + ".mp4";
		// 视频的绝对路径
		mCurrentFilePath = Constant._UPLOAD_Attach_Path_Videos + cropFileName;
		mediaFile = new File(mCurrentFilePath);
		mFileName = mediaFile.getName();
		cropUri = Uri.fromFile(mediaFile);
		return cropUri;

	}

	public void startRecord() throws Exception {

		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(Constant._UPLOAD_Attach_Path_Audios);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			UIHelper.ToastMessage(context, R.string.save_tape_failed_to_no_sdcard);
			return;
		}

		startTime = System.currentTimeMillis();
		button_audio.setBackgroundResource(R.drawable.dotool_audio_doing);
		time = 0;
		handler.post(run);
		dialog_record.show();
		Window w = dialog_record.getWindow();
		w.setContentView(R.layout.record_bg);
		time_record = (TextView) w.findViewById(R.id.time);
		time_record.setText("00:00/00:30");
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		// 音频命名
		String cropFileName = "oa_audio_" + timeStamp + ".aac";
		// 音频的绝对路径
		mCurrentFilePath = Constant._UPLOAD_Attach_Path_Audios + cropFileName;
		File mediaFile = new File(mCurrentFilePath);
		mFileName = mediaFile.getName();
		startRecording();
	}

	public void makeDir(File mediaStorageDir) {
		if (!mediaStorageDir.exists()) {
			mediaStorageDir.mkdirs();
		}

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != RESULT_OK) {
			return;
		}

		switch (requestCode) {

		case ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
			startActivityForResult(
					new Intent(context, ImageCapture.class)
							.putExtra(Constant._IMAGE_URI, origUri)
							.putExtra(Constant._Title, mFileName)
							.putExtra(Constant._IsVisible, true)
							.putExtra(Constant._TAKE_PHOTO_PATH,
									mCurrentFilePath),
					ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);

			break;
		case ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP:
			getUploadTempFile(data.getData());
			startActivityForResult(
					new Intent(context, ImageCapture.class)
							.putExtra(Constant._IMAGE_URI, data.getData())
							.putExtra(Constant._Title, mFileName)
							.putExtra(Constant._IsVisible, true)
							.putExtra(Constant._TAKE_PHOTO_PATH,
									mCurrentFilePath),
					ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);

			break;

		case REQUEST_TAKE_VIDEO:
			Uri videoUri = null;
			if (data == null) {
				// Toast.makeText(context, "视频上传失败", Toast.LENGTH_SHORT).show();
				// return;
				videoUri = Uri.fromFile(new File(mCurrentFilePath));
			} else {
				videoUri = data.getData();
				String scheme = videoUri.getScheme();
				if ("file".equals(scheme)) {
					mCurrentFilePath = videoUri.getPath();
				} else if ("content".equals(scheme)) {
					mCurrentFilePath = MediaUtils.getVideoPath(
							ManageActivity2.this, videoUri);
				}
			}

			startActivityForResult(
					new Intent(context, VideoCapture.class).putExtra(
							Constant._TAKE_VIDEO_PATH, videoUri).putExtra(
							Constant._Title, mFileName),
					REQUEST_TAKE_VIDEO_SAVE);

			break;

		case ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
		case REQUEST_TAKE_VIDEO_SAVE:
			try {
				attachMethod(mCurrentFilePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case RESULT_CHARACTER:// 选分支,提交任务
			commitMethod(null);
			break;
		case SEELECT_CHARACTER:
			List<UsersBeanCopy> NextApprovers = (List<UsersBeanCopy>) data
					.getSerializableExtra("NextApprovers");
			commitMethod(NextApprovers);
			break;
		case RESULT_ATTACHMENT:
			initNetSendMessage();
			break;
		default:
			break;
		}

	}

	private void initNetSendMessage() {
		StringBuffer url = new StringBuffer();
		url.append(domain);
		url.append(URLS._Attach_URL + "&Id="
				+ _id.substring(_id.lastIndexOf("_") + 1) + "&type="
				+ _id.substring(0, _id.lastIndexOf("_")));
		new FormAnsycTask().execute(url.toString(), user, pwd);

	}

	private void initData() {
		context = this;
		mygesture = new GestureDetector(this, this);
		appContext = (AppContext) getApplication();
		_user = appContext.initLoginInfo();
		Intent intent = getIntent();
		_id = intent.getStringExtra(Constant._ID);
		_parent = intent.getStringExtra(Constant._Parent);
		_category = intent.getStringExtra(Constant._Category);
		_title = intent.getStringExtra(Constant._Title);
		_formtype = intent.getStringExtra(Constant._FormType);
		domain = _user.getAddress();
		user = _user.getUsername();
		pwd = _user.getPwd();
		upload_url = _user.getFileServerHandlerUrl();
		Constant._AttachFragment_Path = Environment
				.getExternalStorageDirectory()
				+ "/oa/"
				+ user
				+ "/ActPoint.Data/"
				+ Constant._Attach
				+ "/"
				+ _category
				+ "/"
				+ _parent + "/" + _id;
		Constant._UPLOAD_Attach_Path = Environment
				.getExternalStorageDirectory()
				+ "/oa/"
				+ user
				+ "/ActPoint.Data/"
				+ Constant._Upload
				+ "/"
				+ _category
				+ "/"
				+ _parent + "/" + _id;
		Constant._UPLOAD_Attach_Path_Images = Constant._UPLOAD_Attach_Path
				+ "/images/";
		Constant._UPLOAD_Attach_Path_Audios = Constant._UPLOAD_Attach_Path
				+ "/audios/";
		Constant._UPLOAD_Attach_Path_Videos = Constant._UPLOAD_Attach_Path
				+ "/videos/";

		mPlayer = new MediaPlayer();
		mPlayer.setLooping(false);
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				stopPlaying();

			}
		});
		args = new Args();
	}

	private void initView() {

		context = this;
		back = (ImageView) findViewById(R.id.new_back);
		manager_title = (TextView) findViewById(R.id.new_title);
		manager_title.setText(_title);
		form_title = (TextView) findViewById(R.id.form_title);
		form_origantor = (TextView) findViewById(R.id.form_origantor);

		new_form = (Button) findViewById(R.id.new_form);
		new_text = (Button) findViewById(R.id.new_text);
		new_attachment = (Button) findViewById(R.id.new_attachment);
		new_approval = (Button) findViewById(R.id.new_approval);
		new_todo = (Button) findViewById(R.id.new_todo);

		webform = (WebView) findViewById(R.id.form_webview);
		webtext = (WebView) findViewById(R.id.text_webview);

		if (Constant._OfficialDoc.equals(_formtype)
				|| "ProApp".equals(_formtype)) {
			new_text.setVisibility(View.VISIBLE);
			webtext.setVisibility(View.VISIBLE);
		}

		mpDialog = new ProgressDialog(context);
		mpDialog.setCanceledOnTouchOutside(false);
		mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置风格为圆形进度条
		mpDialog.setMessage("加载中...");
		mpDialog.setIndeterminate(false);// 设置进度条是否为不明确

		attach_list = (ListView) findViewById(R.id.new_attach_list);
		attachadapter = new NewAttachAdapter(context, handler, user, pwd,
				appContext);
		attach_list.setAdapter(attachadapter);

		newapproval_list = (ListView) findViewById(R.id.new_lsComposer);
		approvaladapter = new NewApprovalAdapter(context);
		newapproval_list.setAdapter(approvaladapter);

		layout_content = (LinearLayout) findViewById(R.id.layout_content);
		tool_camera = (Button) findViewById(R.id.tool_camera);
		tool_video = (Button) findViewById(R.id.tool_video);
		tool_picture = (Button) findViewById(R.id.tool_picture);
		button_control = (Button) findViewById(R.id.button_control);
		button_audio = (Button) findViewById(R.id.button_audio);
		tool_attachment = (TextView) findViewById(R.id.tool_attachment);

		reject = (TextView) findViewById(R.id.reject);
		terminal = (TextView) findViewById(R.id.terminal);
		revocation = (TextView) findViewById(R.id.revocation);
		approval = (TextView) findViewById(R.id.approval);
		no_approval = (TextView) findViewById(R.id.no_approval);

		layout_content_edit = (EditText) findViewById(R.id.layout_content_edit);

		back.setOnClickListener(this);
		new_form.setOnClickListener(this);
		new_text.setOnClickListener(this);
		new_attachment.setOnClickListener(this);
		new_approval.setOnClickListener(this);

		if (Constant._Todo.equals(_parent)) {
			new_todo.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (permission == null || "".equals(permission)) {
						Toast.makeText(context, R.string.handle_item_failed_to_no_permission, Toast.LENGTH_SHORT)
								.show();
						// return;
					} else {

						layout_content.startAnimation(AnimationUtils
								.loadAnimation(context,
										R.anim.my_translate_action));
						layout_content.setVisibility(View.VISIBLE);
					}
				}
			});
		} else {
			StringBuffer buffer = new StringBuffer();
			buffer.append(domain);
			buffer.append(URLS._GETBACK_URL);
			buffer.append("&taskIdList="
					+ _id.substring(_id.lastIndexOf("_") + 1));

			new GetBackTask().execute(buffer.toString(), user, pwd);
		}

		tool_camera.setOnClickListener(this);
		tool_video.setOnClickListener(this);
		tool_picture.setOnClickListener(this);
		button_control.setOnClickListener(this);
		button_audio.setOnTouchListener(this);

		tool_attachment.setOnClickListener(this);
		reject.setOnClickListener(this);
		terminal.setOnClickListener(this);
		revocation.setOnClickListener(this);
		approval.setOnClickListener(this);
		no_approval.setOnClickListener(this);

		dialog = new AlertDialog.Builder(this).setOnCancelListener(
				new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						// TODO Auto-generated method stub
						finish();

					}
				}).create();
		dialog.setCanceledOnTouchOutside(false);
		dialog_warning = new AlertDialog.Builder(this).setTitle("警告")
				.setMessage("规则校验失败").setPositiveButton("确定", null).create();
		dialog_warning.setCanceledOnTouchOutside(false);
		alert_dialog = LayoutInflater.from(this).inflate(R.layout.alert_dialog,
				null);
		dialog_deal = new AlertDialog.Builder(this).create();
		dialog_deal.setCanceledOnTouchOutside(false);
		dialog_record = new AlertDialog.Builder(this).setOnCancelListener(
				new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						button_audio
								.setBackgroundResource(R.drawable.dotool_audio);
						handler.removeCallbacks(run);

						try {
							stopRecording();

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						alert_dialog_recorder();
					}
				}).create();
		dialog_record.setCanceledOnTouchOutside(false);
		dialog_record_upload = new AlertDialog.Builder(this).create();
		dialog_record_upload.setCanceledOnTouchOutside(false);
		alert_cancel = (Button) alert_dialog.findViewById(R.id.alert_cancel);
		alert_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog_deal.dismiss();
			}
		});
		alert_ok = (Button) alert_dialog.findViewById(R.id.alert_ok);
		alert_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Revocation_Code.equals(newState)
						|| Terminal_Code.equals(newState)) {

					Args args2 = new Args();
					args2.setAction(1);
					args2.setComment(layout_content_edit.getEditableText()
							.toString());
					args2.setCommentHide(false);
					args2.setEnableTrace(false);
					args2.setNextApprovers(null);
					args2.setResult(Result);
					args2.setStatus(Integer.parseInt(newState));
					args2.setTaskID(Integer.parseInt(_id.substring(_id
							.lastIndexOf("_") + 1)));
					new CommitTask().execute(args2);

				} else if (Reject_Code.equals(newState)) {
					Log.i(tag, "isFirstNode:" + isFirstNode);
					if (isFirstNode) {
						Map<String, String> value = new HashMap<String, String>();
						value.put("taskId",
								_id.substring(_id.lastIndexOf("_") + 1));
						value.put("result", Result);
						value.put("comment", layout_content_edit
								.getEditableText().toString());
						new CommitBackTask().execute(value);
					} else {
						Args args2 = new Args();
						args2.setAction(1);
						args2.setComment(layout_content_edit.getEditableText()
								.toString());
						args2.setCommentHide(false);
						args2.setEnableTrace(false);
						args2.setNextApprovers(null);
						args2.setResult(Result);
						args2.setStatus(Integer.parseInt(newState));
						args2.setTaskID(Integer.parseInt(_id.substring(_id
								.lastIndexOf("_") + 1)));
						new CommitTask().execute(args2);

					}

				} else {
					new CommitTask().execute(args);
				}
			}
		});
		alert_prompt = (TextView) alert_dialog
				.findViewById(R.id.alert_affirm_title);
		alert_result = (TextView) alert_dialog.findViewById(R.id.alert_result);
		// 表单
		webform.getSettings().setJavaScriptEnabled(true);
		webform.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webform.getSettings().setBuiltInZoomControls(true);
		webform.getSettings().setUseWideViewPort(true);
		webform.getSettings().setSupportZoom(true);
		webform.getSettings().setDomStorageEnabled(true);
		webform.getSettings().setAllowFileAccess(true);
		// webform.getSettings().setPluginsEnabled(true);
		webform.getSettings().setUseWideViewPort(true);
		webform.setInitialScale(50);
		webform.getSettings()
				.setUserAgentString(
						"Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_3 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11B511");
		webform.requestFocus();
		webform.getSettings().setLoadWithOverviewMode(true);
		webform.getSettings().setDefaultTextEncodingName("UTF-8");

		webform.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {

				Toast.makeText(context, "Oh no! " + description,
						Toast.LENGTH_SHORT).show();
			}

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);

				if (!((Activity) context).isFinishing()) {
					dialog.show();
					Window window = dialog.getWindow();
					window.setContentView(R.layout.progress);
					TextView progressText = (TextView) window
							.findViewById(R.id.progress_text);
					progressText.setText("页面正在加载...");
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				dialog.dismiss();
			}

			@Override
			public void onReceivedHttpAuthRequest(WebView view,
					HttpAuthHandler handler, String host, String realm) {
				handler.proceed(user, pwd);
			}

		});
		// 正文
		webtext.getSettings().setJavaScriptEnabled(true);
		webtext.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webtext.getSettings().setBuiltInZoomControls(true);
		webtext.getSettings().setUseWideViewPort(true);
		webtext.getSettings().setSupportZoom(true);
		webtext.getSettings().setDomStorageEnabled(true);
		webtext.getSettings().setAllowFileAccess(true);
		// webtext.getSettings().setPluginsEnabled(true);
		webtext.getSettings().setUseWideViewPort(true);
		webtext.setInitialScale(100);
		webtext.getSettings()
				.setUserAgentString(
						"Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_3 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11B511");
		webtext.requestFocus();
		webtext.getSettings().setLoadWithOverviewMode(true);
		webtext.getSettings().setDefaultTextEncodingName("UTF-8");
		scale_init = webtext.getScale();
		webtext.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {

				Toast.makeText(context, "Oh no! " + description,
						Toast.LENGTH_SHORT).show();
			}

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);

				if (!((Activity) context).isFinishing()) {
					dialog.show();
					Window window = dialog.getWindow();
					window.setContentView(R.layout.progress);
					TextView progressText = (TextView) window
							.findViewById(R.id.progress_text);
					progressText.setText("页面正在加载...");
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				webtext.loadUrl("javascript:var tables = document.getElementsByTagName('table'); tables[1].style.display='none'");
				dialog.dismiss();
			}

			@Override
			public void onReceivedHttpAuthRequest(WebView view,
					HttpAuthHandler handler, String host, String realm) {
				handler.proceed(user, pwd);
			}

		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dialog.dismiss();
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.new_back:
			finish();
			break;

		case R.id.new_form:
			new_form.setBackgroundResource(R.drawable.tab_on);
			new_text.setBackgroundResource(R.drawable.tab_down);
			new_attachment.setBackgroundResource(R.drawable.tab_down);
			new_approval.setBackgroundResource(R.drawable.tab_down);

			new_form.setTextColor(Color.WHITE);
			new_text.setTextColor(Color.BLACK);
			new_attachment.setTextColor(Color.BLACK);
			new_approval.setTextColor(Color.BLACK);

			webform.setVisibility(View.VISIBLE);
			webtext.setVisibility(View.GONE);
			attach_list.setVisibility(View.GONE);
			newapproval_list.setVisibility(View.GONE);
			break;
		case R.id.new_text:
			new_form.setBackgroundResource(R.drawable.tab_down);
			new_text.setBackgroundResource(R.drawable.tab_on);
			new_attachment.setBackgroundResource(R.drawable.tab_down);
			new_approval.setBackgroundResource(R.drawable.tab_down);

			new_form.setTextColor(Color.BLACK);
			new_text.setTextColor(Color.WHITE);
			new_attachment.setTextColor(Color.BLACK);
			new_approval.setTextColor(Color.BLACK);

			webform.setVisibility(View.GONE);
			webtext.setVisibility(View.VISIBLE);
			attach_list.setVisibility(View.GONE);
			newapproval_list.setVisibility(View.GONE);
			break;

		case R.id.new_attachment:
			new_form.setBackgroundResource(R.drawable.tab_down);
			new_text.setBackgroundResource(R.drawable.tab_down);
			new_attachment.setBackgroundResource(R.drawable.tab_on);
			new_approval.setBackgroundResource(R.drawable.tab_down);

			new_form.setTextColor(Color.BLACK);
			new_text.setTextColor(Color.BLACK);
			new_attachment.setTextColor(Color.WHITE);
			new_approval.setTextColor(Color.BLACK);

			webform.setVisibility(View.GONE);
			webtext.setVisibility(View.GONE);
			attach_list.setVisibility(View.VISIBLE);
			newapproval_list.setVisibility(View.GONE);

			break;
		case R.id.new_approval:
			new_form.setBackgroundResource(R.drawable.tab_down);
			new_text.setBackgroundResource(R.drawable.tab_down);
			new_attachment.setBackgroundResource(R.drawable.tab_down);
			new_approval.setBackgroundResource(R.drawable.tab_on);

			new_form.setTextColor(Color.BLACK);
			new_text.setTextColor(Color.BLACK);
			new_attachment.setTextColor(Color.BLACK);
			new_approval.setTextColor(Color.WHITE);

			webform.setVisibility(View.GONE);
			webtext.setVisibility(View.GONE);
			attach_list.setVisibility(View.GONE);
			newapproval_list.setVisibility(View.VISIBLE);
			break;
		case R.id.button_control:
			layout_content.setVisibility(View.GONE);
			break;
		case R.id.tool_camera:
			if (canUploadFile) {
				startActionCamera();
			} else {
				UIHelper.ToastMessage(context, R.string.upload_attachment_failed_to_no_permission);
			}
			break;
		case R.id.tool_picture:
			if (canUploadFile) {
				startImagePick();
			} else {
				UIHelper.ToastMessage(context, R.string.upload_attachment_failed_to_no_permission);
			}
			break;
		case R.id.tool_video:
			if (canUploadFile) {
				dispactchTakeVideoIntent();
			} else {
				UIHelper.ToastMessage(context, R.string.upload_attachment_failed_to_no_permission);
			}

			break;
		case R.id.reject:
			alert_message(Constant._Certain_Reject, v, Reject_Code);
			break;
		case R.id.terminal:
			alert_message(Constant._Certain_Terminal, v, Terminal_Code);
			break;
		case R.id.revocation:
			alert_message(Constant._Certain_Revocation, v, Revocation_Code);
			break;
		case R.id.approval:
		case R.id.no_approval:
			alert_message2(v);
			break;
		case R.id.tool_attachment:
			diapactchAttachmentsIntent();
			break;
		}
	}

	public void alert_message(String msg, View v, String state) {
		alert_result.setVisibility(View.GONE);
		alert_prompt.setText(msg);
		dialog_deal.show();
		dialog_deal.getWindow().setContentView(alert_dialog);
		newState = state;
		Result = ((TextView) v).getText().toString();
	}

	/**
	 * 这坨是处理流程中的同意和不同意
	 * @param v
	 */
	public void alert_message2(final View v) {
		mpDialog.show();
		webform.loadUrl("javascript:window.InfoPathSave()");
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mpDialog.dismiss();
				new RuleTask().execute(v);
			}
		}, 3000);

	}

	public void diapactchAttachmentsIntent() {
		Intent intent = new Intent(context, AttachesActivity.class);
		intent.putExtra(Constant._Attach, new_attachments);
		intent.putExtra(Constant._Title, managebean.getTitle());
		intent.putExtra(Constant._ID, managebean.getID());
		intent.putExtra(Constant._WorkflowId, managebean.getWorkflowID());
		startActivityForResult(intent, RESULT_ATTACHMENT);
	}

	private void dispactchTakeVideoIntent() {

		if (!NetWorkHelper.isNetworkAvailable(context)) {
			Toast.makeText(context, R.string.upload_attachment_failed_to_no_network, Toast.LENGTH_SHORT).show();
			return;
		}
		if (!hasSdcard()) {
			Toast.makeText(context, R.string.no_sdcard, Toast.LENGTH_SHORT).show();
			return;
		}
		Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputMediaFile());
		takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
		takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		takeVideoIntent
				.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 10 * 1024 * 1024L);
		if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takeVideoIntent, REQUEST_TAKE_VIDEO);
		}
	}

	/**
	 * 选择图片裁剪
	 * 
	 * @param output
	 */
	private void startImagePick() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		startActivityForResult(Intent.createChooser(intent, "选择图片"),
				ImageUtils.REQUEST_CODE_GETIMAGE_BYCROP);
	}

	public boolean hasSdcard() {
		String sd = Environment.getExternalStorageState();
		if (sd.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 相机拍照
	 * 
	 * @param output
	 */
	private void startActionCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, this.getCameraTempFile());
		startActivityForResult(intent,
				ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);
	}

	// 拍照保存的绝对路径
	private Uri getCameraTempFile() {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(Constant._UPLOAD_Attach_Path_Images);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			UIHelper.ToastMessage(context, R.string.save_img_failed_to_no_sdcard);
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		// 照片命名
		mFileName = "oa_camera_" + timeStamp + ".jpg";
		// 裁剪头像的绝对路径
		mCurrentFilePath = Constant._UPLOAD_Attach_Path_Images + mFileName;
		protraitFile = new File(mCurrentFilePath);
		cropUri = Uri.fromFile(protraitFile);
		origUri = cropUri;
		return cropUri;
	}

	/**
	 * 拍照后裁剪
	 * 
	 * @param data
	 *            原始图片
	 * @param output
	 *            裁剪后图片
	 */
	private void startActionCrop(Uri data) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(data, "image/*");
		intent.putExtra("output", this.getUploadTempFile(data));
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", CROP_WIDTH);// 输出图片大小
		intent.putExtra("outputY", CROP_HEIGHT);
		intent.putExtra("scale", true);// 去黑边
		intent.putExtra("scaleUpIfNeeded", true);// 去黑边
		startActivityForResult(intent,
				ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
	}

	// 裁剪图片的绝对路径
	private Uri getUploadTempFile(Uri uri) {
		String storageState = Environment.getExternalStorageState();
		if (storageState.equals(Environment.MEDIA_MOUNTED)) {
			File savedir = new File(Constant._UPLOAD_Attach_Path_Images);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
		} else {
			UIHelper.ToastMessage(context, R.string.save_img_failed_to_no_sdcard);
			return null;
		}
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		String thePath = ImageUtils.getAbsolutePathFromNoStandardUri(uri);

		// 如果是标准Uri
		if (StringUtils.isEmpty(thePath)) {
			thePath = ImageUtils
					.getAbsoluteImagePath(ManageActivity2.this, uri);
		}
		String ext = FileUtils.getFileFormat(thePath);
		ext = StringUtils.isEmpty(ext) ? "jpg" : ext;
		// 照片命名
		mFileName = "oa_image_" + timeStamp + "." + ext;
		// 裁剪头像的绝对路径
		mCurrentFilePath = Constant._UPLOAD_Attach_Path_Images + mFileName;
		protraitFile = new File(mCurrentFilePath);
		cropUri = Uri.fromFile(protraitFile);
		return cropUri;
	}

	class AttachGetTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.i(tag, "url:" + params[0]);
			try {
				return HttpUtils._get_basic(params[0], params[1], params[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.i(tag, "AttachGetTask:" + result);
			if (result != null && !"".equals(result.trim())) {

				try {
					new_attachments = JsonUtils.parseListsFromJsons(result,
							new TypeToken<LinkedList<Attachment>>() {
							}.getType());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.i(tag, "AttachGetTask:" + new_attachments.size());
				if (new_attachments != null && new_attachments.size() != 0) {
					tool_attachment.setText("+" + new_attachments.size());
				} else {
					tool_attachment.setText("+0");
				}
			}

		}

	}

	class CommitBackTask extends AsyncTask<Map<String, String>, Void, String> {
		protected void onPreExecute() {

			if (!((Activity) context).isFinishing()) {
				dialog.show();
				Window window = dialog.getWindow();
				window.setContentView(R.layout.progress);
				TextView progressText = (TextView) window
						.findViewById(R.id.progress_text);
				progressText.setText("数据提交中...");
				dialog_deal.dismiss();
			}
		};

		@Override
		protected String doInBackground(Map<String, String>... params) {
			// TODO Auto-generated method stub
			String result = null;
			StringBuffer url = new StringBuffer();
			url.append(domain);
			url.append("/ApService/ActPointHandler.ashx?action=WorkflowCancelByTask");
			try {
				result = HttpUtils._post_ntlm(url.toString(), user, pwd, params[0]);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			Log.i(tag, "fkresult:" + result);
			if ("1".equals(result)) {
				setResult(RESULT_OK);
				finish();

			} else {
				Toast.makeText(context, R.string.rollback_failed, Toast.LENGTH_SHORT).show();
			}
		}

	}

	class ApprovalTask extends AsyncTask<String, Void, String> {

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
				progressText.setText("加载中...");
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				return HttpUtils
						._get_ntml(params[0], params[1], params[2], context);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			Log.i(tag, "ApprovalTask:" + result);
			if (result != null && !"".equals(result.trim())) {
				LinkedList<ApprovalBean> agson = JsonUtils.parseUserFromJsons(
						result, new TypeToken<LinkedList<ApprovalBean>>() {
						}.getType());
				approvaladapter.setData(agson);
				approvaladapter.notifyDataSetChanged();
			} else {
				Toast.makeText(context, R.string.data_failed, Toast.LENGTH_SHORT).show();
			}

		}

	}

	class GetBackTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				return HttpUtils
						._get_ntml(params[0], params[1], params[2], context);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			Log.i(tag, "GetBackTask:" + result);
			if (result != null && !"".equals(result.trim())) {
				// 判断是否能够取回
				try {
					JSONObject object = new JSONObject(result);
					String isGetBack = object.getString("Data");
					if ("1".equals(isGetBack)) {
						new_todo.setText("取回");
						new_todo.setClickable(true);
						new_todo.setBackgroundResource(R.drawable.login_button);
						new_todo.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								StringBuffer buffer = new StringBuffer();
								buffer.append(domain);
								buffer.append(URLS._GETBACK_URL_COMMIT);
								buffer.append("&taskIdList="
										+ _id.substring(_id.lastIndexOf("_") + 1));
								new GetBackCommitTask().execute(
										buffer.toString(), user, pwd);
							}
						});
					} else {
						new_todo.setText("处理");
						new_todo.setClickable(false);
						new_todo.setBackgroundResource(R.drawable.login_button_nor);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}
	}

	class GetBackCommitTask extends AsyncTask<String, Void, String> {

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
				progressText.setText("提交中...");
			}

		}

		@Override
		protected String doInBackground(String... params) {

			try {
				return HttpUtils
						._get_ntml(params[0], params[1], params[2], context);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null && !"".equals(result.trim())) {

				try {
					JSONObject object = new JSONObject(result);
					String isGetBackSuccess = object.getString("Data");
					if ("1".equals(isGetBackSuccess)) {
						setResult(RESULT_OK);
						finish();
						Toast.makeText(context, R.string.getback_succeed, Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(context, R.string.getback_failed, Toast.LENGTH_SHORT)
								.show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Toast.makeText(context, R.string.getback_failed, Toast.LENGTH_SHORT).show();
			}
		}

	}

	class FormAnsycTask extends AsyncTask<String, Void, String> {

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
				progressText.setText("加载中...");
			}

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				return HttpUtils
						._get_ntml(params[0], params[1], params[2], context);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (dialog == null)
				return;
			dialog.dismiss();
			if (result != null && !"".equals(result.trim())) {
				try {
					managebean = JsonUtils.parseUserFromJson(result,
							ManageBean.class);
					permission = managebean.getPermission();
					Log.i(tag, "permission:" + permission);
					WorkflowId = managebean.getWorkflowID();
					itemType = managebean.getOaItem().getFormInfo()
							.getFormType();
					formlib = managebean.getOaItem().getFormInfo().getFormLib();
					filename = managebean.getOaItem().getFormInfo()
							.getFilename();

					StringBuffer url_permission = new StringBuffer();
					url_permission.append(domain);
					url_permission.append(URLS._PERMISSION_URL
							+ "&wrapped=0&permissionName="
							+ URLEncoder.encode(permission, "utf-8"));
					new PermissionTask().execute(url_permission.toString(),
							user, pwd);

					switch (FormType.valueOf(itemType)) {
					case Text:
						webform.loadDataWithBaseURL("", managebean.getOaItem()
								.getFormInfo().getBodyText(), "text/html",
								"UTF-8", "");
						break;
					case InfoPath:
						itemContent = URLS.FORM_INFOPATH + "/" + formlib + "/"
								+ filename;

						webform.loadUrl(itemContent);
						break;
					case WebPage:
					case Page:
						break;
					case Word:
					case Excel:
					case PowerPoint:
						itemContent = URLS.FORM_HTTP + "/" + formlib + "/"
								+ filename;

						webform.loadUrl(itemContent);
						break;
					case OfficialDoc:
						itemContent = URLS.FORM_INFOPATH + "/" + formlib + "/"
								+ filename;
						webform.loadUrl(itemContent);
						break;

					default:
						break;
					}

					if (Constant._OfficialDoc.equals(_formtype)
							|| "ProApp".equals(_formtype)) {
						webtext.loadUrl(URLS.FORM_HTTP
								+ managebean.getOaItem().getFormInfo()
										.getBodyText().split("\\|")[managebean
										.getOaItem().getFormInfo()
										.getBodyText().split("\\|").length - 1]);
					}

					if (managebean.getOaItem().getAttachments() != null) {
						attachadapter.setData(managebean.getOaItem()
								.getAttachments());
						attachadapter.notifyDataSetChanged();
					}

					form_title.setText(managebean.getTitle() + "");
					form_origantor.setText("发起人: "
							+ managebean.getOriginatorUser().getUserName()
							+ "   " + managebean.getOaItem().getCreated());

					StringBuffer url = new StringBuffer();
					url.append(domain);
					url.append(URLS._Approval_URL
							+ "&workflowId="
							+ URLEncoder.encode(managebean.getWorkflowID(),
									"utf-8"));
					Log.i("----------", url.toString());
					new ApprovalTask().execute(url.toString(), user, pwd);

					StringBuffer buf = new StringBuffer();
					buf.append(upload_url);
					buf.append(URLS._ATTACH_GET_URL);
					buf.append("&workflowId=" + managebean.getWorkflowID());
					buf.append("&taskId="
							+ _id.substring(_id.lastIndexOf("_") + 1));
					buf.append("&Created="
							+ URLEncoder.encode(managebean.getCreated(),
									"utf-8"));
					new AttachGetTask().execute(buf.toString(), user, pwd);
					new FirstNodeTask().execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Toast.makeText(context, R.string.data_failed, Toast.LENGTH_SHORT).show();
			}
		}

	}

	private void startRecording() throws Exception {
		try {
			mRecorder = new MediaRecorder();
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
			mRecorder.setOutputFile(mCurrentFilePath);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mRecorder.setMaxDuration(30000);
			mRecorder.prepare();
			mRecorder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void stopRecord() throws Exception {
		long intervalTime = System.currentTimeMillis() - startTime;
		dialog_record.dismiss();
		button_audio.setBackgroundResource(R.drawable.dotool_audio);
		handler.removeCallbacks(run);

		try {
			stopRecording();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (intervalTime < MIN_INTERVAL_TIME) {
			Toast.makeText(context, "时间太短！", Toast.LENGTH_SHORT).show();
			File file = new File(mCurrentFilePath);
			file.delete();
			return;
		}

		alert_dialog_recorder();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (v == button_audio) {
			if (!canUploadFile) {
				UIHelper.ToastMessage(context, R.string.upload_attachment_failed_to_no_permission);
				return false;
			}
			int action = event.getAction();
			switch (action) {
			case MotionEvent.ACTION_DOWN:

				try {
					startRecord();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case MotionEvent.ACTION_UP:

				try {
					stopRecord();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		} else if (v == webtext) {
			return mygesture.onTouchEvent(event);
		}

		return false;
	}

	public void attachMethod(String filepath) throws Exception {

		if (!NetWorkHelper.isNetworkAvailable(context)) {
			Toast.makeText(context, R.string.upload_attachment_failed_to_no_network, Toast.LENGTH_SHORT).show();
			return;
		}
		if (!hasSdcard()) {
			Toast.makeText(context, R.string.no_sdcard, Toast.LENGTH_SHORT).show();
			return;
		}

		
		//压缩图片测试
		
		File file = new File(ImageUtils.compressPic(appContext, filepath));
//		File file = new File(filepath);
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("Action", "UploadWorkItemAttachment");
		param.put("workflowId", WorkflowId);
		param.put("taskId", _id.substring(_id.lastIndexOf("_") + 1));
		param.put(
				"dir",
				"/Files/"
						+ new SimpleDateFormat("yyyyMM", Locale.CHINA)
								.format(new Date()) + File.separator
						+ WorkflowId + File.separator
						+ _id.substring(_id.lastIndexOf("_") + 1));
		param.put("local", "1");
		param.put("ext", "file");

		new UploadTask().execute(upload_url, param, file);
	}

	class PermissionTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Log.i("permissiontask","");
			}
		

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.i("permissiontask",params.toString());
			try {
				return HttpUtils
						._get_ntml(params[0], params[1], params[2], context);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.i(tag, "permission:" + result);
			if (result != null && !"".equals(result.trim())) {
				LinkedList<PermissionBean> mPermissionBeans = null;
				try {
					mPermissionBeans = JsonUtils.parseListsFromJsons(result,
							new TypeToken<LinkedList<PermissionBean>>() {
							}.getType());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.i("______parent",_parent);
				if (mPermissionBeans != null && mPermissionBeans.size() != 0 && _parent.equals("Todo")) {
					for (PermissionBean mPermissionBean : mPermissionBeans) {

						if ("同意".equals(mPermissionBean.getName())
								|| "已阅".equals(mPermissionBean.getName())) {
							approval.setVisibility(View.VISIBLE);
							if ("同意".equals(approval.getText().toString())) {
								approval.setText("同意");
							} else {
								approval.setText(mPermissionBean.getName());
							}
						} else if ("不同意".equals(mPermissionBean.getName())) {
							no_approval.setVisibility(View.VISIBLE);
						} else if ("撤销".equals(mPermissionBean.getName())) {
							revocation.setVisibility(View.VISIBLE);
						} else if ("中止".equals(mPermissionBean.getName())) {
							terminal.setVisibility(View.VISIBLE);
						} else if ("回退".equals(mPermissionBean.getName())) {
							reject.setVisibility(View.VISIBLE);
						} else if ("已阅".equals(mPermissionBean.getName())) {
							approval.setVisibility(View.VISIBLE);
							approval.setText(mPermissionBean.getName());
						} else if ("上传附件".equals(mPermissionBean.getName())) {
							canUploadFile = true;
						} else if ("已处理".equals(mPermissionBean.getName())) {
							approval.setVisibility(View.VISIBLE);
							approval.setText(mPermissionBean.getName());
						}
					}
				}
			}
		}

	}

	public String uploadSubmit(String url, Map<String, String> param, File file) {
		HttpClient httpClient = new DefaultHttpClient();
		StringBuffer sb = new StringBuffer();
		try {
			HttpPost post = new HttpPost(url);

			MultipartEntity entity = new MultipartEntity();
			if (param != null && !param.isEmpty()) {
				for (Map.Entry<String, String> entry : param.entrySet()) {
					entity.addPart(entry.getKey(),
							new StringBody(entry.getValue()));
				}
			}
			// 添加文件参数
			if (file != null && file.exists()) {
				entity.addPart(
						"file",
						new FileBody(file, URLEncoder.encode(file.getName(),
								"utf-8"), "application/octet-stream", "utf-8"));
			}
			post.setEntity(entity);

			HttpResponse response = httpClient.execute(post);

			int stateCode = response.getStatusLine().getStatusCode();

			if (stateCode == HttpStatus.SC_OK) {
				HttpEntity result = response.getEntity();
				if (result != null) {
					InputStream is = result.getContent();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is));
					String tempLine;
					while ((tempLine = br.readLine()) != null) {
						sb.append(tempLine);

					}
				}
			}
			post.abort();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return sb.toString();
	}

	public class UploadTask extends AsyncTask<Object, Integer, String> {
		String url;
		Map<String, String> map;
		File file;
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (!((Activity) context).isFinishing()) {
				pd = new ProgressDialog(context);
				pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pd.setMessage("文件上传中...");
				pd.show();
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		protected String doInBackground(Object... params) {
			// TODO Auto-generated method stub

			try {
				url = (String) params[0];
				map = (Map<String, String>) params[1];
				file = (File) params[2];
				return uploadSubmit(url, map, file);

			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			Log.i(tag, "result:" + result);
			if (result != null && !"".equals(result.trim())) {
				initNetSendMessage();
				Toast.makeText(context, R.string.upload_succeed, Toast.LENGTH_SHORT).show();
			} else {
				//文件上传失败时也有返回信息，此处应校验返回信息
				Toast.makeText(context, R.string.upload_failed, Toast.LENGTH_SHORT).show();
			}
		}

	}

	class RuleTask extends AsyncTask<View, Void, String> {

		View v;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (!((Activity) context).isFinishing()) {
				dialog.show();
				Window window = dialog.getWindow();
				window.setContentView(R.layout.progress);
				TextView progressText = (TextView) window
						.findViewById(R.id.progress_text);
				progressText.setText("加载中...");
			}

		}

		@Override
		protected String doInBackground(View... params) {
			// TODO Auto-generated method stub
			try {
				v = params[0];
				StringBuffer url = new StringBuffer();
				url.append(domain);
				url.append(URLS._Rule);
				url.append("&taskId=" + _id.substring(_id.lastIndexOf("_") + 1));
				return HttpUtils._get_ntml(url.toString(), user, pwd, context);

			} catch (Exception e) {
				e.printStackTrace();

			}
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null && !"".equals(result.trim())) {
				Type listType = new TypeToken<LinkedList<RuleBean>>() {
				}.getType();
				LinkedList<RuleBean> rules = JsonUtils.parseUserFromJsons(
						result, listType);
				if (rules != null) {
					isRuleOk = true;
					for (RuleBean bean : rules) {
						if ("NotNull".equals(bean.getRuleType())
								&& bean.isIsResultOK() == false) {
							dialog_warning.setMessage(bean.getResultString()
									+ "");
							dialog_warning.show();
							isRuleOk = false;
							break;
						}

					}
					if (isRuleOk) {
						newState = Approval_Code;
						Result = ((TextView) v).getText().toString();
						new BranchTask().execute(_id.substring(_id
								.lastIndexOf("_") + 1));
					}

				} else {
					dialog_warning.show();
				}

			} else {
				dialog_warning.show();
			}
		}

	}

	class BranchTask extends AsyncTask<String, Void, String> {

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
				progressText.setText("获取数据中...");
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				StringBuffer url = new StringBuffer();
				url.append(domain);
				url.append(URLS._BranchTask + "&taskId=" + params[0]);
				return HttpUtils._get_ntml(url.toString(), user, pwd, context);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null && !"".equals(result.trim())) {

				BranchBean mBranchBean = null;
				try {
					mBranchBean = JsonUtils.parseObjectFromJson(result,
							BranchBean.class);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (mBranchBean != null) {
					String resultmode = mBranchBean.getResultMode();
					int type = mBranchBean.getType();

					if ("1".equals(resultmode) && type == 3) {
						// 选择分支界面
						startActivityForResult(
								new Intent(context, NewBranchActivity.class)
										.putExtra(Constant._Title, _title)
										.putExtra(Constant._Branch, mBranchBean)
										.putExtra(Constant._WorkflowId,
												WorkflowId), RESULT_CHARACTER);

					} else {
						// 选择选人界面
						new GetNextApproverInfoTask().execute(_id.substring(_id
								.lastIndexOf("_") + 1));
					}
				} else {
					Toast.makeText(context, R.string.data_failed, Toast.LENGTH_SHORT)
							.show();
				}

			} else {
				Toast.makeText(context, R.string.data_failed, Toast.LENGTH_SHORT).show();
			}
		}

	}

	class GetNextApproverInfoTask extends AsyncTask<String, Void, String> {

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
				progressText.setText("数据获取中");
			}
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				StringBuffer url = new StringBuffer();
				url.append(domain);
				url.append(URLS._GetNextApprover + "&taskId=" + params[0]
						+ "&result=" + URLEncoder.encode(Result, "utf-8"));
				return HttpUtils._get_ntml(url.toString(), user, pwd, context);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null && !"".equals(result.trim())) {
				Type listType = new TypeToken<LinkedList<UsersBean>>() {
				}.getType();
				LinkedList<UsersBean> mUsersBeans = null;
				try {
					mUsersBeans = JsonUtils.parseListsFromJsons(result,
							listType);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (mUsersBeans.size() != 0) {
					for (UsersBean bean : mUsersBeans) {
						String ResultMode = bean.getResultMode();
						if ("SelectUser".equals(ResultMode)) {
							// 跳到选人
							Toast.makeText(context, "选人", Toast.LENGTH_SHORT)
									.show();
							startActivityForResult(
									new Intent(context,
											NewSelectCharacter.class)
											.putExtra(Constant._Select_Users,
													mUsersBeans).putExtra(
													Constant._Title, _title),
									SEELECT_CHARACTER);
							isSelectUser = true;
							return;

						}
					}
					if (!isSelectUser) {
						commitMethod(null);
					}
				} else {
					// 直接提交
					commitMethod(null);
				}

			} else {
				// 直接提交
				commitMethod(null);

			}

		}

	}

	public void commitMethod(List<UsersBeanCopy> NextApprovers) {
		alert_result.setVisibility(View.VISIBLE);
		alert_prompt.setText("你确定提交事项吗?");
		alert_result.setText("审批结果:" + Result);
		dialog_deal.show();
		dialog_deal.getWindow().setContentView(alert_dialog);

		args.setAction(1);
		args.setComment(layout_content_edit.getEditableText().toString());
		args.setCommentHide(false);
		args.setEnableTrace(false);
		// 选分支
		args.setNextApprovers(NextApprovers);
		args.setTaskID(Integer.parseInt(_id.substring(_id.lastIndexOf("_") + 1)));
		args.setStatus(Integer.parseInt(newState));
		args.setResult(Result);
	}

	class CommitTask extends AsyncTask<Args, Void, String> {

		protected void onPreExecute() {

			if (!((Activity) context).isFinishing()) {
				dialog.show();
				Window window = dialog.getWindow();
				window.setContentView(R.layout.progress);
				TextView progressText = (TextView) window
						.findViewById(R.id.progress_text);
				progressText.setText("数据提交中...");
				dialog_deal.dismiss();
			}
		};

		@Override
		protected String doInBackground(Args... params) {
			// TODO Auto-generated method stub
			StringBuffer url = new StringBuffer();
			url.append(domain);
			url.append("/ApService/ActPointHandler.ashx");
			String result = null;
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("Action", "CommitTask");
			try {
				map.put("args", JsonUtils.parseObejectToString(params[0]));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				result = HttpUtils._post_ntlm(url.toString(), user, pwd, map);
				Log.i(tag, "result:" + result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			if ("1".equals(result)) {
				setResult(RESULT_OK);
				finish();

			} else {
				Toast.makeText(context, R.string.handle_failed, Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}

		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}

	class FirstNodeTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				StringBuffer url = new StringBuffer();
				url.append(domain);
				url.append(URLS._ISFirstNode);
				url.append("&taskId=" + _id.substring(_id.lastIndexOf("_") + 1));
				return HttpUtils._get_ntml(url.toString(), user, pwd, context);

			} catch (Exception e) {
				e.printStackTrace();

			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.i(tag, "_ISFirstNode:" + result);
			isFirstNode = Boolean.parseBoolean(result);
		}

	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		float scale = webtext.getScale();
		if (scale > scale_init)
			return false;

		if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			Log.i(tag, "right");
			webtext.loadUrl("javascript:function go(next){var a = document.querySelector('#defaultform>:nth-child(4)>tbody>tr>td>:nth-child('+ (next ? 4 : 2) + ')');if (a.tagName === 'IMG') {return;}location.href = a.href;};go()");
		} // 向右
		if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			Log.i(tag, "left");
			// web.loadUrl("javascript:function a(){location.href = document.querySelector('#defaultform table a:last-child').href};a();");
			webtext.loadUrl("javascript:function go(next){var a = document.querySelector('#defaultform>:nth-child(4)>tbody>tr>td>:nth-child('+ (next ? 4 : 2) + ')');if (a.tagName === 'IMG') {return;}location.href = a.href;};go(true)");
		}
		return false;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub

		webtext.setOnTouchListener(this);
		return super.dispatchTouchEvent(ev);
	}
}
