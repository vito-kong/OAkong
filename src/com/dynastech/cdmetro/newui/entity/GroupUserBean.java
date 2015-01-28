package com.dynastech.cdmetro.newui.entity;

import com.dynastech.cdmetro.beans.UserBean;

public class GroupUserBean {
	
	private String group_user_name,login_name;
	private boolean isGroup,isChecked;
	private UserBean user;
	
	public String getGroup_user_name() {
		return group_user_name;
	}
	public void setGroup_user_name(String group_user_name) {
		this.group_user_name = group_user_name;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public boolean isGroup() {
		return isGroup;
	}
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	
	
	
}
