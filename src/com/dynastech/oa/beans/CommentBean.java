package com.dynastech.oa.beans;

import java.util.LinkedList;

public class CommentBean {

	String Id;
	String Title;
	String Result;
	String Comment;
	String CommentHide;
	String Created;
	Orignators UserInfo;

	public LinkedList<Attachments> getAttachments() {
		return Attachments;
	}

	public void setAttachments(LinkedList<Attachments> attachments) {
		Attachments = attachments;
	}

	LinkedList<Attachments> Attachments;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
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

	public String getCommentHide() {
		return CommentHide;
	}

	public void setCommentHide(String commentHide) {
		CommentHide = commentHide;
	}

	public String getCreated() {
		return Created;
	}

	public void setCreated(String created) {
		Created = created;
	}

	public Orignators getUserInfo() {
		return UserInfo;
	}

	public void setUserInfo(Orignators userInfo) {
		UserInfo = userInfo;
	}

}
