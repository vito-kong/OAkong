package com.dynastech.oa.task;

import java.util.LinkedList;

import com.dynastech.cdmetro.AppContext;
import com.dynastech.cdmetro.newui.util.UIHelper;
import com.dynastech.oa.entity.DocumentsData;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class DocumentsTask extends AsyncTask<String, Void, LinkedList<DocumentsData>> {

	private AppContext appContext;
	private Context ctx;
	private AlertDialog dialog;
	private Handler handler;
	private boolean isRefresh;
	
	public DocumentsTask(AppContext paramAppContext, Context paramContext,
			Handler paramHandler, boolean paramBoolean) {
		this.appContext = paramAppContext;
		this.ctx = paramContext;
		this.handler = paramHandler;
		this.isRefresh = paramBoolean;
	}
	

	protected void onPreExecute() {
		super.onPreExecute();
		if (!isRefresh)
			dialog = UIHelper.createLoadingDialog(ctx, "数据加载中...");
	}
	@Override
	protected LinkedList<DocumentsData> doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			return appContext._getDocumentData(params[0], params[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(LinkedList<DocumentsData> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (dialog != null)
			dialog.dismiss();
		Message message = new Message();
		if (result != null) {
			message.what = 1;
			message.obj = result;
		} else {
			message.what = -1;
		}
		handler.sendMessage(message);
	}

}
