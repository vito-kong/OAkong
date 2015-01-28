package com.dynastech.cdmetro.newui.entity;

import java.io.Serializable;

public class Attachment implements Serializable{

	private String fileName;
	private String url;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
