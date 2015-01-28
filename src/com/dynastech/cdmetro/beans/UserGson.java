package com.dynastech.cdmetro.beans;

import java.util.LinkedList;

public class UserGson {

	private boolean Success;
	private Orignators Data;
	private String Message;

	public boolean isSuccess() {
		return Success;
	}

	public void setSuccess(boolean success) {
		Success = success;
	}

	public Orignators getData() {
		return Data;
	}

	public void setData(Orignators data) {
		Data = data;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}
}
