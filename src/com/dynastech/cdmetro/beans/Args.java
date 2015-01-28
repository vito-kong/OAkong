package com.dynastech.cdmetro.beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Args implements Serializable {

	private static final long serialVersionUID = 1L;
	private int Action;
	private String Comment;
	private boolean CommentHide;
	private boolean EnableTrace;
	private List<UsersBeanCopy> NextApprovers;
	private int TaskID;
	private int Status;
	private String Result;

	public int getAction() {
		return Action;
	}

	public void setAction(int action) {
		Action = action;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		Comment = comment;
	}

	public boolean isCommentHide() {
		return CommentHide;
	}

	public void setCommentHide(boolean commentHide) {
		CommentHide = commentHide;
	}

	public boolean isEnableTrace() {
		return EnableTrace;
	}

	public void setEnableTrace(boolean enableTrace) {
		EnableTrace = enableTrace;
	}

	public List<UsersBeanCopy> getNextApprovers() {
		return NextApprovers;
	}

	public void setNextApprovers(List<UsersBeanCopy> nextApprovers) {
		NextApprovers = nextApprovers;
	}

	public int getTaskID() {
		return TaskID;
	}

	public void setTaskID(int taskID) {
		TaskID = taskID;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}
}
