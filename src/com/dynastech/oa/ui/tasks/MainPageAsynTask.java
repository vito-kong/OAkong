package com.dynastech.oa.ui.tasks;

import com.dynastech.oa.AppContext;
import com.dynastech.oa.AppException;
import com.dynastech.oa.ui.entity.HomeData;
import com.dynastech.oa.utils.UIHelper;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class MainPageAsynTask extends AsyncTask<String, Void, Object> {

	private AppContext appContext;
	private Context ctx;
	private Handler handler;
	private String tag = "MainPageAsynTask";
	private AlertDialog dialog;
	private boolean isRefresh;
	
	public MainPageAsynTask(AppContext appContext,Context ctx,Handler handler,boolean isRefresh) {
		// TODO Auto-generated constructor stub
		this.appContext = appContext;
		this.ctx = ctx;
		this.handler = handler;
		this.isRefresh=isRefresh;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if (!isRefresh) {
			dialog=UIHelper.createLoadingDialog(ctx,"数据加载中...");
		}
		
	}
	@Override
	protected Object doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			return appContext._getHomeData(params[0], params[1], params[2]);
		} catch (AppException e) {
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
			if (result instanceof HomeData) {// 获取主页数据成功
				HomeData data=(HomeData) result;
				msg.what = 1;// 成功
				msg.obj = data;
			}else if (result instanceof AppException) {//  获取主页数据失败
				AppException e = (AppException) result;
				msg.what = -1;
				msg.obj = e;
			} else {
				msg.what =0;//自己刷新
			}
			handler.sendMessage(msg);
		}
	}

}
