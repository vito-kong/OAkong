package com.dynastech.oa.beans;

import java.io.Serializable;
import java.util.LinkedList;

public class OaItemBean implements Serializable {

	private String ID;
	private String Title;
	private String WorkflowID;
	private FormInfoBean FormInfo;
	private String Created;
	private OriginatorUserBean OriginatorUser;
	private LinkedList<AttachmentsBean> Attachments;

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

	public String getWorkflowID() {
		return WorkflowID;
	}

	public void setWorkflowID(String workflowID) {
		WorkflowID = workflowID;
	}

	public FormInfoBean getFormInfo() {
		return FormInfo;
	}

	public void setFormInfo(FormInfoBean formInfo) {
		FormInfo = formInfo;
	}

	public String getCreated() {
		return Created;
	}

	public void setCreated(String created) {
		Created = created;
	}

	public OriginatorUserBean getOriginatorUser() {
		return OriginatorUser;
	}

	public void setOriginatorUser(OriginatorUserBean originatorUser) {
		OriginatorUser = originatorUser;
	}

	public LinkedList<AttachmentsBean> getAttachments() {
		return Attachments;
	}

	public void setAttachments(LinkedList<AttachmentsBean> attachments) {
		Attachments = attachments;
	}

}
