package com.dynastech.oa.beans;

import java.util.LinkedList;

public class ItemContentGson {

	private boolean success;
	private LinkedList<ItemContent> data;
	private String message;

	public LinkedList<ItemContent> getData() {
		return data;
	}

	public void setData(LinkedList<ItemContent> data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
