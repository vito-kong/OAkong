package com.dynastech.oa.beans;

import java.io.Serializable;
import java.util.LinkedList;

public class UsersBeanCopy implements Serializable {

	private String ApproverType;
	private String Name;
	private LinkedList<UserBeanCopy> Users;
	private String ResultMode;
	private int ApprovalCount;

	public String getApproverType() {
		return ApproverType;
	}

	public void setApproverType(String approverType) {
		ApproverType = approverType;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public LinkedList<UserBeanCopy> getUsers() {
		return Users;
	}

	public void setUsers(LinkedList<UserBeanCopy> users) {
		Users = users;
	}

	public String getResultMode() {
		return ResultMode;
	}

	public void setResultMode(String resultMode) {
		ResultMode = resultMode;
	}

	public int getApprovalCount() {
		return ApprovalCount;
	}

	public void setApprovalCount(int approvalCount) {
		ApprovalCount = approvalCount;
	}
}
