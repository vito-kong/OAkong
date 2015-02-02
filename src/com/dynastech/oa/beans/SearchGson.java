package com.dynastech.oa.beans;

import java.util.LinkedList;

public class SearchGson {
	private boolean Success;
	private LinkedList<SearchBean> Data;
	private String Message;

	public boolean isSuccess() {
		return Success;
	}

	public void setSuccess(boolean success) {
		Success = success;
	}

	public LinkedList<SearchBean> getData() {
		return Data;
	}

	public void setData(LinkedList<SearchBean> data) {
		Data = data;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
