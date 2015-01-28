package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class ActivityBeanCopy implements Serializable {

	private String Title;
	private String ApprovalBy;
	private int Status;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getApprovalBy() {
		return ApprovalBy;
	}

	public void setApprovalBy(String approvalBy) {
		ApprovalBy = approvalBy;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

}
