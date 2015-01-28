package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class FormInfoBean implements Serializable {

	private String FormType;
	private String FormLib;
	private String Filename;
	private String ListTitle;
	private int ItemID;
	private String BodyText;

	public String getFormType() {
		return FormType;
	}

	public void setFormType(String formType) {
		FormType = formType;
	}

	public String getFormLib() {
		return FormLib;
	}

	public void setFormLib(String formLib) {
		FormLib = formLib;
	}

	public String getFilename() {
		return Filename;
	}

	public void setFilename(String filename) {
		Filename = filename;
	}

	public String getListTitle() {
		return ListTitle;
	}

	public void setListTitle(String listTitle) {
		ListTitle = listTitle;
	}

	public int getItemID() {
		return ItemID;
	}

	public void setItemID(int itemID) {
		ItemID = itemID;
	}

	public String getBodyText() {
		return BodyText;
	}

	public void setBodyText(String bodyText) {
		BodyText = bodyText;
	}

}
