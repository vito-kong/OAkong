package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class Item implements Serializable {

	public static final int ITEM = 0;
	public static final int SECTION = 1;

	public int type;
	public String text;
	public int file_type;
	public String extension;
	public AttachmentsBean bean;
	private boolean barVisible;
	
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public int getFile_type() {
		return file_type;
	}

	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}

	

	public AttachmentsBean getBean() {
		return bean;
	}

	public void setBean(AttachmentsBean bean) {
		this.bean = bean;
	}

	public Item(int type, String text) {
		this.type = type;
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	public boolean isBarVisible() {
		return barVisible;
	}

	public void setBarVisible(boolean barVisible) {
		this.barVisible = barVisible;
	}
}
