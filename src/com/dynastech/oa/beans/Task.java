package com.dynastech.oa.beans;

public class Task {

	private int id;
	private int ItemID;
	private String WorkflowID;
	private String Title;
	private String Originator;
	private String Text;
	private String SubmittedBy;
	private String Modified;
	private String IconUrl;
	private String Result;
	private int Status;
	private String Comment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getItemID() {
		return ItemID;
	}

	public void setItemID(int itemID) {
		ItemID = itemID;
	}

	public String getWorkflowID() {
		return WorkflowID;
	}

	public void setWorkflowID(String workflowID) {
		WorkflowID = workflowID;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getOriginator() {
		return Originator;
	}

	public void setOriginator(String originator) {
		Originator = originator;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public String getSubmittedBy() {
		return SubmittedBy;
	}

	public void setSubmittedBy(String submittedBy) {
		SubmittedBy = submittedBy;
	}

	public String getModified() {
		return Modified;
	}

	public void setModified(String modified) {
		Modified = modified;
	}

	public String getIconUrl() {
		return IconUrl;
	}

	public void setIconUrl(String iconUrl) {
		IconUrl = iconUrl;
	}

	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}
}
