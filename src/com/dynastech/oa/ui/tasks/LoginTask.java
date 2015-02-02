package com.dynastech.oa.ui.tasks;

import com.dynastech.oa.AppContext;
import com.dynastech.oa.AppException;
import com.dynastech.oa.R;
import com.dynastech.oa.ui.entity.User;
import com.dynastech.oa.ui.util.UIHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class LoginTask extends AsyncTask<String, Void, Object> {

	private AppContext appContext;
	private Handler handler;
	private String account, pwd, address;
	private boolean isAutoLogin, isRemember;
	private String tag = "LoginTask";
	private AlertDialog dialog;
	private Context ctx;

	public LoginTask(AppContext appContext, Context ctx, Handler handler,
			boolean isAutoLogin, boolean isRemember, String address) 
	{
		this.appContext = appContext;
		this.ctx = ctx;
		this.handler = handler;
		this.isAutoLogin = isAutoLogin;
		this.isRemember = isRemember;
		this.address = address;
	}

	/*
	   AsyncTask的执行分为四个步骤，每一步都对应一个回调方法，开发者需要实现这些方法。
　  * 1) 继承AsyncTask
　   * 2) 实现AsyncTask中定义的下面一个或几个方法
       * onPreExecute(), 该方法将在执行实际的后台操作前被UI 线程调用。可以在该方法中做一些准备工作，如在界面上显示一个进度条，或者一些控件的实例化，这个方法可以不用实现。
       * doInBackground(Params...), 将在onPreExecute 方法执行后马上执行，该方法运行在后台线程中。这里将主要负责执行那些很耗时的后台处理工作。可以调用 publishProgress方法来更新实时的任务进度。该方法是抽象方法，子类必须实现。
      * onProgressUpdate(Progress...),在publishProgress方法被调用后，UI 线程将调用这个方法从而在界面上展示任务的进展情况，例如通过一个进度条进行展示。
      * onPostExecute(Result), 在doInBackground 执行完成后，onPostExecute 方法将被UI 线程调用，后台的计算结果将通过该方法传递到UI 线程，并且在界面上展示给用户.
      * onCancelled(),在用户取消线程操作的时候调用。在主线程中调用onCancelled()的时候调用。
	 */
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		if (!((Activity) ctx).isFinishing()) 
		{
			dialog = new AlertDialog.Builder(ctx).create();
			dialog.setCanceledOnTouchOutside(false);
			//取消登录的一个回调
			dialog.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) 
				{
					Toast.makeText(ctx, "已取消登录", Toast.LENGTH_SHORT).show();
					cancel(true);
				}
			});
			dialog.show();
			Window window = dialog.getWindow();
			window.setContentView(R.layout.progress);
			TextView progressText = (TextView) window
					.findViewById(R.id.progress_text);
			progressText.setText("登录验证中...");
		}
		
	}

	
	//登录完成后，调用全局类方法中的loginVerify去验证是否登录成功
	@Override
	protected Object doInBackground(String... params) {


	   Log.v("Params012", params[0].toString()+"^"+params[1].toString()+"^"+params[2].toString());
	   Log.v("Params345", params[3].toString()+"^"+params[4].toString()+"^"+params[5].toString());

		try 
		{
			account = params[1];
			pwd = params[2];
			return appContext.loginVerify(params[0], params[1], params[2],
					params[3], params[4], params[5], params[6]);
		} catch (AppException e) 
		{
			e.printStackTrace();
			return e;
		}
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(dialog!=null)
			dialog.dismiss();
		
		Message msg = new Message();
		if (result != null) {
			if (result instanceof User) {
				// 登陆成功
				User user = (User) result;
				user.setUsername(account);
				user.setPwd(pwd);
				user.setAutoLogin(isAutoLogin);
				user.setRememberMe(isRemember);
				user.setAddress(address);
			
				appContext.saveLoginInfo(user);
				msg.what = 1;// 成功
				msg.obj = user;
			} else if (result instanceof AppException) 
			{
				// 登录失败
				AppException e = (AppException) result;
				msg.what = -1;
				msg.obj = e;
				Log.v("LoginError", e.toString());

			}
			
		} else {
			msg.what =0;
		}
		handler.sendMessage(msg);

	}

}
