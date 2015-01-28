package com.dynastech.cdmetro.beans;

public class Document {

	private int id;
	private int ParentID;
	private String IconUrl;
	private String Category;
	private String ItemType;
	private String Text;
	private int RecordCount;
	private int UnReadCount;
	private boolean IsUnRead;
	private String UpdateTime;
	private String ReadUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentID() {
		return ParentID;
	}

	public void setParentID(int parentID) {
		ParentID = parentID;
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

	public String getItemType() {
		return ItemType;
	}

	public void setItemType(String itemType) {
		ItemType = itemType;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}

	public int getUnReadCount() {
		return UnReadCount;
	}

	public void setUnReadCount(int unReadCount) {
		UnReadCount = unReadCount;
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

}
