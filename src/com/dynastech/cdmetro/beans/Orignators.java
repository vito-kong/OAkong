package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class Orignators implements Serializable {

	private String LoginName;
	private String DisplayName;
	private String AvatarUrl;
	private boolean isChecked;
	private boolean isVisual = true;

	public String getAvatarUrl() {
		return AvatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		AvatarUrl = avatarUrl;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getLoginName() {
		return LoginName;
	}

	public void setLoginName(String loginName) {
		LoginName = loginName;
	}

	public String getDisplayName() {
		return DisplayName;
	}

	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}

	public boolean isVisual() {
		return isVisual;
	}

	public void setVisual(boolean isVisual) {
		this.isVisual = isVisual;
	}

}
