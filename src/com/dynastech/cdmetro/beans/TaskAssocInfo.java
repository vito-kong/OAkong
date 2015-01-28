package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class TaskAssocInfo implements Serializable{

	private int AdditionID;
	private int AttachmentID;
	private String AttachmentList;
	private String OaLink;
	
	public int getAdditionID() {
		return AdditionID;
	}
	public void setAdditionID(int additionID) {
		AdditionID = additionID;
	}
	public int getAttachmentID() {
		return AttachmentID;
	}
	public void setAttachmentID(int attachmentID) {
		AttachmentID = attachmentID;
	}
	public String getAttachmentList() {
		return AttachmentList;
	}
	public void setAttachmentList(String attachmentList) {
		AttachmentList = attachmentList;
	}
	public String getOaLink() {
		return OaLink;
	}
	public void setOaLink(String oaLink) {
		OaLink = oaLink;
	}
	
}
