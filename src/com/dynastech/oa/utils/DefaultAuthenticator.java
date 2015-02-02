package com.dynastech.oa.utils;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import android.app.Activity;
import android.content.Context;

public class DefaultAuthenticator extends Authenticator {

	private Context ctx;
	public DefaultAuthenticator(Context c) {
		// TODO Auto-generated constructor stub
		ctx=c;
	}
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		// TODO Auto-generated method stub
		String user=ctx.getSharedPreferences(ConfigInfo.USER_PREFERENCES, Activity.MODE_PRIVATE)
				.getString(ConfigInfo.PREF_USERNAME, "");
		String pwd=ctx.getSharedPreferences(ConfigInfo.USER_PREFERENCES,Activity.MODE_PRIVATE)
				.getString(ConfigInfo.PREF_PASSWORD, "");
		return new PasswordAuthentication (user, pwd.toCharArray());
	}
	
}
