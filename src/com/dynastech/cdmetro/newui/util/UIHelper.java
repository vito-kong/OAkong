package com.dynastech.cdmetro.newui.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import com.dynastech.cdmetro.AppManager;
import com.dynastech.oa.R;
import com.dynastech.cdmetro.email.MailSenderInfo;
import com.dynastech.cdmetro.email.SimpleMailSender;
import com.dynastech.cdmetro.newui.common.Constant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */

public class UIHelper {

	public final static int LISTVIEW_ACTION_INIT = 0x01;
	public final static int LISTVIEW_ACTION_REFRESH = 0x02;
	public final static int LISTVIEW_ACTION_SCROLL = 0x03;
	public final static int LISTVIEW_ACTION_CHANGE_CATALOG = 0x04;

	public final static int LISTVIEW_DATA_MORE = 0x01;
	public final static int LISTVIEW_DATA_LOADING = 0x02;
	public final static int LISTVIEW_DATA_FULL = 0x03;
	public final static int LISTVIEW_DATA_EMPTY = 0x04;

	public final static int LISTVIEW_DATATYPE_NEWS = 0x01;
	public final static int LISTVIEW_DATATYPE_BLOG = 0x02;
	public final static int LISTVIEW_DATATYPE_POST = 0x03;
	public final static int LISTVIEW_DATATYPE_TWEET = 0x04;
	public final static int LISTVIEW_DATATYPE_ACTIVE = 0x05;
	public final static int LISTVIEW_DATATYPE_MESSAGE = 0x06;
	public final static int LISTVIEW_DATATYPE_COMMENT = 0x07;

	public final static int REQUEST_CODE_FOR_RESULT = 0x01;
	public final static int REQUEST_CODE_FOR_REPLY = 0x02;

	/** 表情图片匹配 */
	private static Pattern facePattern = Pattern
			.compile("\\[{1}([0-9]\\d*)\\]{1}");

	/** 全局web样式 */
	public final static String WEB_STYLE = "<style>* {font-size:16px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} "
			+ "img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} "
			+ "pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;} "
			+ "a.tag {font-size:15px;text-decoration:none;background-color:#bbd6f3;border-bottom:2px solid #3E6D8E;border-right:2px solid #7F9FB6;color:#284a7b;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;}</style>";

	/**
	 * 发送App异常崩溃报告
	 * 
	 * @param cont
	 * @param crashReport
	 */
	public static void sendAppCrashReport(final Context cont,
			final String crashReport) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_error);
		builder.setMessage(R.string.app_error_message);
		builder.setPositiveButton(R.string.submit_report,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// // 发送异常报告
						// Intent i = new Intent(Intent.ACTION_SEND);
						// // i.setType("text/plain"); //模拟器
						// i.setType("message/rfc822"); // 真机
						// i.putExtra(Intent.EXTRA_EMAIL,
						// new String[] { "qy86615069@163.com" });
						// i.putExtra(Intent.EXTRA_SUBJECT,
						// "cdmetro - 错误报告");
						// i.putExtra(Intent.EXTRA_TEXT, crashReport);
						// cont.startActivity(Intent.createChooser(i,
						// "发送错误报告"));
						// // 退出
						
						if(NetWorkHelper.isNetworkAvailable(cont)){
//							new EmailSendTask(cont, "正在发送错误报告,稍等...")
//							.execute("cdmetro-错误报告",crashReport);
							sendErrorMessage(cont, "正在发送错误报告,稍等...","cdmetro-错误报告_"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()),crashReport);
						}else{
							Toast.makeText(cont, "网络未连接,错误报告发送失败", 
									Toast.LENGTH_SHORT).show();
							AppManager.getAppManager().AppExit(cont);
						}
					
					}
				});
		builder.setNegativeButton(R.string.exit,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 退出
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.show();
	}

	public static void sendErrorMessage(Context ctx,String message,String email_title,String email_content){
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost(Constant.email_host);
		mailInfo.setMailServerPort(Constant.email_port);
		mailInfo.setValidate(true);
		mailInfo.setUserName(Constant.email_from_address); // 你的邮箱地址
		mailInfo.setPassword(Constant.email_from_pwd);// 您的邮箱密码
		mailInfo.setFromAddress(Constant.email_from_address);
		mailInfo.setToAddress(Constant.email_to_address);
		mailInfo.setSubject(email_title);
		mailInfo.setContent(email_content);
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);
		AppManager.getAppManager().AppExit(ctx);
	}
	/**
	 * 弹出Toast消息
	 * 
	 * @param msg
	 */

	static class EmailSendTask extends AsyncTask<String, Void, Boolean> {

		private Context ctx;
		private String message;
		private AlertDialog mDialog;
		
		public EmailSendTask(Context mContext,String msg) {
			// TODO Auto-generated constructor stub
			ctx=mContext;
			message=msg;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mDialog=createLoadingDialog(ctx, message);
			
		}
		
		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setMailServerHost(Constant.email_host);
			mailInfo.setMailServerPort(Constant.email_port);
			mailInfo.setValidate(true);
			mailInfo.setUserName(Constant.email_from_address); // 你的邮箱地址
			mailInfo.setPassword(Constant.email_from_pwd);// 您的邮箱密码
			mailInfo.setFromAddress(Constant.email_from_address);
			mailInfo.setToAddress(Constant.email_to_address);
			mailInfo.setSubject(params[0]);
			mailInfo.setContent(params[1]);
			SimpleMailSender sms = new SimpleMailSender();
			return sms.sendTextMail(mailInfo);// 发送文体格式
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.i("EmailSendTask", "result:"+result);
			mDialog.dismiss();
			if(result){
				Toast.makeText(ctx, "错误报告发送成功", Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(ctx, "错误报告发送失败,请检查网络", Toast.LENGTH_SHORT).show();
			}
			AppManager.getAppManager().AppExit(ctx);
		}

	}

	public static void ToastMessage(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}

	/**
	 * 数据加载
	 * 
	 * */
	public static AlertDialog createLoadingDialog(Context ctx, String message) {
		if (!((Activity) ctx).isFinishing()) {
			AlertDialog dialog = new AlertDialog.Builder(ctx).create();
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
			Window window = dialog.getWindow();
			window.setContentView(R.layout.progress);
			TextView progressText = (TextView) window
					.findViewById(R.id.progress_text);
			progressText.setText(message);
			return dialog;
		}
		return null;
	}

	/**
	 * 退出程序
	 * 
	 * @param cont
	 */
	public static void Exit(final Context cont) {
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_menu_surelogout);
		builder.setPositiveButton(R.string.sure,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 退出
						AppManager.getAppManager().AppExit(cont);
					}
				});
		builder.setNegativeButton(R.string.cancle,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}
}
