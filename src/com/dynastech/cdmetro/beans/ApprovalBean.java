package com.dynastech.cdmetro.beans;

import java.util.LinkedList;

import com.dynastech.cdmetro.newui.entity.CommentReplyBean;

public class ApprovalBean {

	private String ID;
	private String Title;
	private String Result;
	private String Comment;
	private String StartDate;
	private String UserInfo;
	private LinkedList<AttachmentsBean> Attachments;
	private LinkedList<CommentReplyBean> CommentReply;
	
	public LinkedList<CommentReplyBean> getCommentReply() {
		return CommentReply;
	}

	public void setCommentReply(LinkedList<CommentReplyBean> commentReply) {
		CommentReply = commentReply;
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

	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = startDate;
	}

	public String getUserInfo() {
		return UserInfo;
	}

	public void setUserInfo(String userInfo) {
		UserInfo = userInfo;
	}

	public LinkedList<AttachmentsBean> getAttachments() {
		return Attachments;
	}

	public void setAttachments(LinkedList<AttachmentsBean> attachments) {
		Attachments = attachments;
	}

}
