package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class Data implements Serializable {
	private int ID;
	private String Parent;
	private String IconUrl;
	private String Category;
	private String Text;
	private String RecordCount;
	private String UnReadCount;
	private String Content;
	private ContextBean2 Context;
	private boolean Enabled;//地铁中
	
	public String getParent() {
		return Parent;
	}

	public void setParent(String parent) {
		Parent = parent;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getIconUrl() {
		return IconUrl;
	}

	public void setIconUrl(String iconUrl) {
		IconUrl = iconUrl;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public String getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(String recordCount) {
		RecordCount = recordCount;
	}

	public String getUnReadCount() {
		return UnReadCount;
	}

	public void setUnReadCount(String unReadCount) {
		UnReadCount = unReadCount;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public ContextBean2 getContext() {
		return Context;
	}

	public void setContext(ContextBean2 context) {
		Context = context;
	}

	public boolean isEnabled() {
		return Enabled;
	}

	public void setEnabled(boolean enabled) {
		Enabled = enabled;
	}

}
