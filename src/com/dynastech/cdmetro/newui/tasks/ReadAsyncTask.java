package com.dynastech.cdmetro.newui.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.dynastech.cdmetro.newui.common.Constant;
import com.dynastech.cdmetro.utils.HttpUtils;

public 	class ReadAsyncTask extends AsyncTask<String, Void, String> {

	Context mContext;
	public ReadAsyncTask(Context context) {
		// TODO Auto-generated constructor stub
		mContext=context;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String result = null;
		try {
			StringBuffer url = new StringBuffer();
			url.append(params[0]);
			url.append(Constant._READ_URL);
			url.append("Id="+ params[1]);
			result = HttpUtils._get_ntml(url.toString(), params[2], params[3],mContext);
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

	}

}
