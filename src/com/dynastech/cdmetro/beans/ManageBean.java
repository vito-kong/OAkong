package com.dynastech.cdmetro.beans;

import java.io.Serializable;
import java.util.LinkedList;

public class ManageBean implements Serializable {

	private String ID;
	private String Title;
	private String OaID;
	private String TaskType;
	private String WorkflowID;
	private OaItemBean OaItem;
	private OriginatorUserBean OriginatorUser;
	private String CurrentGuid;
	private String Permission;
	private LinkedList<AttachmentsBean> Attachments;
	private String FormType;
	private String Modified;
	private String Created;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getOaID() {
		return OaID;
	}

	public void setOaID(String oaID) {
		OaID = oaID;
	}

	public String getTaskType() {
		return TaskType;
	}

	public void setTaskType(String taskType) {
		TaskType = taskType;
	}

	public String getWorkflowID() {
		return WorkflowID;
	}

	public void setWorkflowID(String workflowID) {
		WorkflowID = workflowID;
	}

	public OaItemBean getOaItem() {
		return OaItem;
	}

	public void setOaItem(OaItemBean oaItem) {
		OaItem = oaItem;
	}

	public OriginatorUserBean getOriginatorUser() {
		return OriginatorUser;
	}

	public void setOriginatorUser(OriginatorUserBean originatorUser) {
		OriginatorUser = originatorUser;
	}

	public String getCurrentGuid() {
		return CurrentGuid;
	}

	public void setCurrentGuid(String currentGuid) {
		CurrentGuid = currentGuid;
	}

	public String getPermission() {
		return Permission;
	}

	public void setPermission(String permission) {
		Permission = permission;
	}

	public LinkedList<AttachmentsBean> getAttachments() {
		return Attachments;
	}

	public void setAttachments(LinkedList<AttachmentsBean> attachments) {
		Attachments = attachments;
	}

	public String getFormType() {
		return FormType;
	}

	public void setFormType(String formType) {
		FormType = formType;
	}

	public String getModified() {
		return Modified;
	}

	public void setModified(String modified) {
		Modified = modified;
	}

	public String getCreated() {
		return Created;
	}

	public void setCreated(String created) {
		Created = created;
	}

}
