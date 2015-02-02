package com.dynastech.oa.ui.entity;

public class CommentReplyBean extends Entity {
	private String Author;
	private String Comment;
	private String Created;
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public String getCreated() {
		return Created;
	}
	public void setCreated(String created) {
		Created = created;
	}
	
	
}
