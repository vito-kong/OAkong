package com.dynastech.oa.beans;

import java.io.Serializable;
import java.util.LinkedList;

public class BranchBeanCopy implements Serializable {

	private LinkedList<ActivityBeanCopy> Activities;
	private int ApprovalCount;
	private int Status;
	private int Type;

	public LinkedList<ActivityBeanCopy> getActivities() {
		return Activities;
	}

	public void setActivities(LinkedList<ActivityBeanCopy> activities) {
		Activities = activities;
	}

	public int getApprovalCount() {
		return ApprovalCount;
	}

	public void setApprovalCount(int approvalCount) {
		ApprovalCount = approvalCount;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

}
