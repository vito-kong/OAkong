package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class TaskFormInfo implements Serializable{

	private String AppTitle;
	private String AppUrl;
	private String BodyText;
	private String Filename;
	private String FormType;
	private String ListTitle;
	
	public String getAppTitle() {
		return AppTitle;
	}
	public void setAppTitle(String appTitle) {
		AppTitle = appTitle;
	}
	public String getAppUrl() {
		return AppUrl;
	}
	public void setAppUrl(String appUrl) {
		AppUrl = appUrl;
	}
	public String getBodyText() {
		return BodyText;
	}
	public void setBodyText(String bodyText) {
		BodyText = bodyText;
	}
	public String getFilename() {
		return Filename;
	}
	public void setFilename(String filename) {
		Filename = filename;
	}
	public String getFormType() {
		return FormType;
	}
	public void setFormType(String formType) {
		FormType = formType;
	}
	public String getListTitle() {
		return ListTitle;
	}
	public void setListTitle(String listTitle) {
		ListTitle = listTitle;
	}
	
}
