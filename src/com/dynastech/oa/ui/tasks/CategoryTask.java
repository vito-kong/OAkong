package com.dynastech.oa.ui.tasks;

import com.dynastech.oa.AppContext;
import com.dynastech.oa.AppException;
import com.dynastech.oa.ui.entity.CategoryData;
import com.dynastech.oa.ui.entity.HomeData;
import com.dynastech.oa.ui.entity.User;
import com.dynastech.oa.utils.UIHelper;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class CategoryTask extends AsyncTask<Object, Void, Object> {
	private AppContext appContext;
	private Context ctx;
	private String tag = "CategoryTask";
	private boolean isRefresh;
	private AlertDialog dialog;
	private Handler handler;

	public CategoryTask(AppContext appContext, Context ctx, Handler handler,
			boolean isRefresh) {
		this.appContext = appContext;
		this.ctx = ctx;
		this.handler = handler;
		this.isRefresh = isRefresh;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if (!isRefresh) {
			dialog = UIHelper.createLoadingDialog(ctx,"数据加载中...");
		}
	}

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		try {
			return appContext
					._getCategoryData((User) params[0], (String) params[1],
							(String) params[2], (Integer) params[3],(Boolean)params[4],(String)params[5]);
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
			if (result instanceof CategoryData) {// 获取列表数据成功
				CategoryData data=(CategoryData) result;
				msg.what = 1;// 成功
				msg.obj = data;
			}else if (result instanceof AppException) {//  获取列表数据失败
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
