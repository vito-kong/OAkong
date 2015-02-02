package com.dynastech.oa.ui.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.dynastech.oa.ui.common.Constant;
import com.dynastech.oa.utils.HttpUtils;
import com.dynastech.oa.utils.URLS;

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
			url.append(URLS._READ_URL);
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
