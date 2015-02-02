package com.dynastech.oa.beans;

import java.util.LinkedList;

public class ApprovalGson {

	private boolean Success;
	private LinkedList<Approval> Data;
	private String Message;

	public boolean isSuccess() {
		return Success;
	}

	public void setSuccess(boolean success) {
		Success = success;
	}

	public LinkedList<Approval> getData() {
		return Data;
	}

	public void setData(LinkedList<Approval> data) {
		Data = data;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
