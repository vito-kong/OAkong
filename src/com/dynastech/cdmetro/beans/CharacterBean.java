package com.dynastech.cdmetro.beans;

import java.io.Serializable;
import java.util.LinkedList;

public class CharacterBean implements Serializable {

	private String ActGuid;
	private String ApproverType;
	private String Name;
	private LinkedList<UserBean> Users;
	private String ResultMode;
	private int ApprovalCount;

	public String getActGuid() {
		return ActGuid;
	}

	public void setActGuid(String actGuid) {
		ActGuid = actGuid;
	}

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

	public LinkedList<UserBean> getUsers() {
		return Users;
	}

	public void setUsers(LinkedList<UserBean> users) {
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
