package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class UserBean implements Serializable {

	private String UserName;
	private String LoginName;
	private String SipAddress;
	private boolean isChecked;
	private boolean isVisual = true;
	private boolean isGroup;
	
	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isVisual() {
		return isVisual;
	}

	public void setVisual(boolean isVisual) {
		this.isVisual = isVisual;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getLoginName() {
		return LoginName;
	}

	public void setLoginName(String loginName) {
		LoginName = loginName;
	}

	public String getSipAddress() {
		return SipAddress;
	}

	public void setSipAddress(String sipAddress) {
		SipAddress = sipAddress;
	}

	public boolean isGroup() {
		return isGroup;
	}

	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

}
