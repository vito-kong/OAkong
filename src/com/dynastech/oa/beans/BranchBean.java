package com.dynastech.oa.beans;

import java.io.Serializable;
import java.util.LinkedList;

public class BranchBean implements Serializable {

	private String GUID;
	private LinkedList<ActivityBean> Activities;
	private int ApprovalCount;
	private int Status;
	private int Type;
	private String ResultMode;
	private int Index;
	private String Path;
	private int DisableModify;
	
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

	public LinkedList<ActivityBean> getActivities() {
		return Activities;
	}

	public void setActivities(LinkedList<ActivityBean> activities) {
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

	public String getResultMode() {
		return ResultMode;
	}

	public void setResultMode(String resultMode) {
		ResultMode = resultMode;
	}

	
}
