package com.dynastech.cdmetro.beans;

import java.io.Serializable;
import java.util.LinkedList;

public class OrignatorGson implements Serializable {

	private boolean Success;
	private LinkedList<Role> Data;
	private String Message;

	public boolean isSuccess() {
		return Success;
	}

	public void setSuccess(boolean success) {
		Success = success;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public LinkedList<Role> getData() {
		return Data;
	}

	public void setData(LinkedList<Role> data) {
		Data = data;
	}

}
