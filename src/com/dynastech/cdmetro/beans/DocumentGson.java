package com.dynastech.cdmetro.beans;

import java.util.LinkedList;

public class DocumentGson {

	private boolean Success;
	private LinkedList<Document> Data;
	private String Message;

	public boolean isSuccess() {
		return Success;
	}

	public void setSuccess(boolean success) {
		Success = success;
	}

	public LinkedList<Document> getData() {
		return Data;
	}

	public void setData(LinkedList<Document> data) {
		Data = data;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

}
