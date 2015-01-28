package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class LoginBean implements Serializable {

	private UserInfoBean UserInfo;
	private AttenceBean Attence;
	private String FileServerHandlerUrl;

	public UserInfoBean getUserInfo() {
		return UserInfo;
	}

	public void setUserInfo(UserInfoBean userInfo) {
		UserInfo = userInfo;
	}

	public AttenceBean getAttence() {
		return Attence;
	}

	public void setAttence(AttenceBean attence) {
		Attence = attence;
	}

	public String getFileServerHandlerUrl() {
		return FileServerHandlerUrl;
	}

	public void setFileServerHandlerUrl(String fileServerHandlerUrl) {
		FileServerHandlerUrl = fileServerHandlerUrl;
	}

}
