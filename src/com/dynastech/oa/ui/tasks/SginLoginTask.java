package com.dynastech.oa.ui.tasks;

import java.util.Map;

import com.dynastech.oa.AppException;
import com.dynastech.oa.ui.api.ApiClient;
import com.dynastech.oa.ui.entity.LoginGson;
import com.dynastech.oa.utils.JsonUtils;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SginLoginTask extends AsyncTask<Object, Void, String> {

	private Handler handler;

	public SginLoginTask(Handler paramHandler) {
		this.handler = paramHandler;
	}

	@Override
	protected String doInBackground(Object... params) {
		
//		Object[] mObject=(Object[]) params[0];
		// TODO Auto-generated method stub
		try {
			return ApiClient._post2((String) params[0], (Map) params[1]);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		return null;
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		 Message localMessage = new Message();
		 Log.i("SginLoginTask", "result:"+result);
		 if(result!=null&&!"".equals(result)&&!"[]".equals(result)){
			  LoginGson localLoginGson = null;
			try {
				localLoginGson = (LoginGson)JsonUtils.
						  parseObjectFromJson(result, LoginGson.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  localMessage.what=2;
			  localMessage.obj = localLoginGson;
		      this.handler.sendMessage(localMessage);
		 }
		    
	}
	

}
