package com.dynastech.oa.task;

import com.dynastech.cdmetro.AppContext;
import com.dynastech.cdmetro.newui.util.UIHelper;
import com.dynastech.oa.entity.ContactData;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class ContactAsyncTask extends AsyncTask<String, Void, ContactData> {

	private AppContext appContext;
	private Context ctx;
	private AlertDialog dialog;
	private Handler handler;
	private boolean isRefresh;

	public ContactAsyncTask(AppContext paramAppContext, Context paramContext,
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
	protected ContactData doInBackground(String... params) {
		// TODO Auto-generated method stub
		ContactData contactdata;
		try {
			contactdata = appContext._getContactData(params[0], params[1],
					params[2], params[3]);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		return contactdata;
	}

	protected void onPostExecute(ContactData contactdata) {
		super.onPostExecute(contactdata);
		if (dialog != null)
			dialog.dismiss();
		Message message = new Message();
		if (contactdata != null) {
			message.what = 1;
			message.obj = contactdata;
		} else {
			message.what = -1;
		}
		handler.sendMessage(message);
	}
}
