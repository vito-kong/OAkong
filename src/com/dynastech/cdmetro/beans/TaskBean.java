package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class TaskBean implements Serializable{

	private String TempID;
	private String WorkflowID;
	private String Description;
	private boolean EnableTrace;
	private String Title;
	private TaskFormInfo FormInfo;
	private TaskAssocInfo AssocInfo;
	private int OaID;
	private int OATemplateID;
	private int TemplateID;
	private String FirstUsers;
//	private String Users;
	
	public String getTempID() {
		return TempID;
	}
	public void setTempID(String tempID) {
		TempID = tempID;
	}
	public String getWorkflowID() {
		return WorkflowID;
	}
	public void setWorkflowID(String workflowID) {
		WorkflowID = workflowID;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public boolean isEnableTrace() {
		return EnableTrace;
	}
	public void setEnableTrace(boolean enableTrace) {
		EnableTrace = enableTrace;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public TaskFormInfo getFormInfo() {
		return FormInfo;
	}
	public void setFormInfo(TaskFormInfo formInfo) {
		FormInfo = formInfo;
	}
	public TaskAssocInfo getAssocInfo() {
		return AssocInfo;
	}
	public void setAssocInfo(TaskAssocInfo assocInfo) {
		AssocInfo = assocInfo;
	}
	public int getOaID() {
		return OaID;
	}
	public void setOaID(int oaID) {
		OaID = oaID;
	}
	
	public int getTemplateID() {
		return TemplateID;
	}
	public void setTemplateID(int templateID) {
		TemplateID = templateID;
	}
	public String getFirstUsers() {
		return FirstUsers;
	}
	public void setFirstUsers(String firstUsers) {
		FirstUsers = firstUsers;
	}
	public int getOATemplateID() {
		return OATemplateID;
	}
	public void setOATemplateID(int oATemplateID) {
		OATemplateID = oATemplateID;
	}
//	public String getUsers() {
//		return Users;
//	}
//	public void setUsers(String users) {
//		Users = users;
//	}
	
}
