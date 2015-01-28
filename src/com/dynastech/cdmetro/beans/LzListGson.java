package com.dynastech.cdmetro.beans;

import java.util.LinkedList;

public class LzListGson {

	private boolean Success;
	private LinkedList<LzList> Data;
	private String Message;

	public boolean isSuccess() {
		return Success;
	}

	public void setSuccess(boolean success) {
		Success = success;
	}

	public LinkedList<LzList> getData() {
		return Data;
	}

	public void setData(LinkedList<LzList> data) {
		Data = data;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
