package com.dynastech.oa.beans;

import java.util.LinkedList;

public class TaskGson {

	private boolean Success;
	private LinkedList<Task> Data;
	private String Message;

	public boolean isSuccess() {
		return Success;
	}

	public void setSuccess(boolean success) {
		Success = success;
	}

	public LinkedList<Task> getData() {
		return Data;
	}

	public void setData(LinkedList<Task> data) {
		Data = data;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
