package com.dynastech.cdmetro.beans;

import java.util.LinkedList;

public class Commentent {

	public LinkedList<CommentBean> getComments() {
		return Comments;
	}

	public void setComments(LinkedList<CommentBean> comments) {
		Comments = comments;
	}

	private LinkedList<CommentBean> Comments;
}
