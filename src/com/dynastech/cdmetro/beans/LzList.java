package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class LzList implements Serializable{

	private String ID;
	private String ParentID;
	private String IconUrl;
	private String Category;
	private String ItemType;
	private String Title;
	private String ViceTitle;
	private boolean IsParent;
	private int RecordCount;
	private int UnReadCount;
	private boolean IsUnRead;// 文件未看过
	private String UpdateTime;
	private String ReadUrl;// 文件的url
	private ContextBean Context;

	public int getUnReadCount() {
		return UnReadCount;
	}

	public void setUnReadCount(int unReadCount) {
		UnReadCount = unReadCount;
	}

	public String getParentID() {
		return ParentID;
	}

	public void setParentID(String parentID) {
		ParentID = parentID;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public boolean isIsUnRead() {
		return IsUnRead;
	}

	public void setIsUnRead(boolean isUnRead) {
		IsUnRead = isUnRead;
	}

	public String getUpdateTime() {
		return UpdateTime;
	}

	public void setUpdateTime(String updateTime) {
		UpdateTime = updateTime;
	}

	public String getReadUrl() {
		return ReadUrl;
	}

	public void setReadUrl(String readUrl) {
		ReadUrl = readUrl;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getViceTitle() {
		return ViceTitle;
	}

	public void setViceTitle(String viceTitle) {
		ViceTitle = viceTitle;
	}

	public String getItemType() {
		return ItemType;
	}

	public void setItemType(String itemType) {
		ItemType = itemType;
	}

	public String getIconUrl() {
		return IconUrl;
	}

	public void setIconUrl(String iconUrl) {
		IconUrl = iconUrl;
	}

	public boolean isIsParent() {
		return IsParent;
	}

	public void setIsParent(boolean isParent) {
		IsParent = isParent;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}

	public ContextBean getContext() {
		return Context;
	}

	public void setContext(ContextBean context) {
		Context = context;
	}

}
