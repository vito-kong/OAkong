package com.dynastech.cdmetro.beans;

import java.util.LinkedList;

public class Approval {

	String Title;
	LinkedList<CommentBean> Comments;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public LinkedList<CommentBean> getComments() {
		return Comments;
	}

	public void setComments(LinkedList<CommentBean> comments) {
		Comments = comments;
	}

}
