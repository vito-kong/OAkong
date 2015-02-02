package com.dynastech.oa.beans;

import java.util.LinkedList;

public class ReadGson {

	private boolean Success;
	private LinkedList<ReadBean> Data;
	private String Message;

	public boolean isSuccess() {
		return Success;
	}

	public void setSuccess(boolean success) {
		Success = success;
	}

	public LinkedList<ReadBean> getData() {
		return Data;
	}

	public void setData(LinkedList<ReadBean> data) {
		Data = data;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
