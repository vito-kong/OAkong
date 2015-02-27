package com.dynastech.oa.ui.entity;

import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

import com.dynastech.oa.AppException;
import com.dynastech.oa.utils.JsonUtils;


public class User {

	private UserInfo UserInfo;
	private Attence Attence;
	private String FileServerHandlerUrl;
	private String address;
	private boolean isRememberMe;
	private boolean isAutoLogin;
	private String username;
	private String pwd;
	
	public UserInfo getUserInfo() {
		return UserInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		UserInfo = userInfo;
	}
	public Attence getAttence() {
		return Attence;
	}
	public void setAttence(Attence attence) {
		Attence = attence;
	}
	public String getFileServerHandlerUrl() {
		return FileServerHandlerUrl;
	}
	public void setFileServerHandlerUrl(String fileServerHandlerUrl) {
		FileServerHandlerUrl = fileServerHandlerUrl;
	}
	public boolean isRememberMe() {
		return isRememberMe;
	}
	public void setRememberMe(boolean isRememberMe) {
		this.isRememberMe = isRememberMe;
	}
	public boolean isAutoLogin() {
		return isAutoLogin;
	}
	public void setAutoLogin(boolean isAutoLogin) {
		this.isAutoLogin = isAutoLogin;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public static User parse(String jsonData) throws Exception {
		Log.i("User", "user:"+jsonData);
		return JsonUtils.parseObjectFromJson(jsonData, User.class);
		
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
