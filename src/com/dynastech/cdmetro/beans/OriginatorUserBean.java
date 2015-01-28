package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class OriginatorUserBean implements Serializable {

	private String UserName;
	private String LoginName;
	private String SipAddress;

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

}
