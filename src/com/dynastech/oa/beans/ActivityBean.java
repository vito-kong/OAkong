package com.dynastech.oa.beans;

import java.io.Serializable;

public class ActivityBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Title;
	private String GUID;
	private int Index;
	private int Type;
	private String Path;
	private int DisableModify;
	private int FactApprovalCount;
	private int TimeOut;
	private String Tag;
	private String Permission;
	private String ApprovalBy;
	private String ApprovalBy_Users;
	private boolean isChecked;
	private int Status;
	
	public String getGUID() {
		return GUID;
	}

	public void setGUID(String gUID) {
		GUID = gUID;
	}

	public int getIndex() {
		return Index;
	}

	public void setIndex(int index) {
		Index = index;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public String getPath() {
		return Path;
	}

	public void setPath(String path) {
		Path = path;
	}

	public int getDisableModify() {
		return DisableModify;
	}

	public void setDisableModify(int disableModify) {
		DisableModify = disableModify;
	}

	public int getFactApprovalCount() {
		return FactApprovalCount;
	}

	public void setFactApprovalCount(int factApprovalCount) {
		FactApprovalCount = factApprovalCount;
	}

	public int getTimeOut() {
		return TimeOut;
	}

	public void setTimeOut(int timeOut) {
		TimeOut = timeOut;
	}

	public String getTag() {
		return Tag;
	}

	public void setTag(String tag) {
		Tag = tag;
	}



	public String getPermission() {
		return Permission;
	}

	public void setPermission(String permission) {
		Permission = permission;
	}

	private boolean isClicked;
	private String ResultMode;
	private boolean isHasChooseUsers;
	private String AssignedTo;
	
	public String getResultMode() {
		return ResultMode;
	}

	public void setResultMode(String resultMode) {
		ResultMode = resultMode;
	}

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

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public boolean isClicked() {
		return isClicked;
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

	public boolean isHasChooseUsers() {
		return isHasChooseUsers;
	}

	public void setHasChooseUsers(boolean isHasChooseUsers) {
		this.isHasChooseUsers = isHasChooseUsers;
	}

	public String getApprovalBy_Users() {
		return ApprovalBy_Users;
	}

	public void setApprovalBy_Users(String approvalBy_Users) {
		ApprovalBy_Users = approvalBy_Users;
	}

	public String getAssignedTo() {
		return AssignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		AssignedTo = assignedTo;
	}

}
