package com.dynastech.cdmetro.beans;

import java.util.LinkedList;

public class AttentionGson {

	private boolean Success;
	private LinkedList<AttentionBean> Data;
	private String Message;

	public boolean isSuccess() {
		return Success;
	}

	public void setSuccess(boolean success) {
		Success = success;
	}

	public LinkedList<AttentionBean> getData() {
		return Data;
	}

	public void setData(LinkedList<AttentionBean> data) {
		Data = data;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
