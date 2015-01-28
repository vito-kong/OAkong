package com.dynastech.cdmetro.beans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AttachmentsBean implements Serializable {

	private String Title;
	private String Filename;
	private String Url;
	private int Size;
	private String Suffix;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getFilename() {
		return Filename;
	}

	public void setFilename(String filename) {
		Filename = filename;
	}

	public String getUrl() {
//		if(Url==null||Url.isEmpty()){
//			return "";
//		}
//		int index=Url.lastIndexOf("/");
//		String str=Url.substring(0, index+1);
//		try {
//			str+=URLEncoder.encode(Url.substring(index+1),"utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		return str;
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public int getSize() {
		return Size;
	}

	public void setSize(int size) {
		Size = size;
	}

	public String getSuffix() {
		return Suffix;
	}

	public void setSuffix(String suffix) {
		Suffix = suffix;
	}

}
