package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class UnRead implements Serializable {

	private int id;
	private String text;
	private String notify_type;
	private String created_time;
	private Source source;
	private From from;

	public UnRead() {
		super();
	}

	public UnRead(int id, String text, String notify_type, String created_time,
			Source source, From from) {
		super();
		this.id = id;
		this.text = text;
		this.notify_type = notify_type;
		this.created_time = created_time;
		this.source = source;
		this.from = from;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getNotify_type() {
		return notify_type;
	}

	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}

	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public From getFrom() {
		return from;
	}

	public void setFrom(From from) {
		this.from = from;
	}

}
