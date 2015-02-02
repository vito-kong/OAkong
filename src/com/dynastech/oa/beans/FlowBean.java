package com.dynastech.oa.beans;

public class FlowBean {

	private int ID;
	private String Title;
	private int ParentID;
	private String WorkflowName;
	private int WorkflowTempletID;
	private String DocType;
	private String BodyText;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public int getParentID() {
		return ParentID;
	}
	public void setParentID(int parentID) {
		ParentID = parentID;
	}
	public String getWorkflowName() {
		return WorkflowName;
	}
	public void setWorkflowName(String workflowName) {
		WorkflowName = workflowName;
	}
	public int getWorkflowTempletID() {
		return WorkflowTempletID;
	}
	public void setWorkflowTempletID(int workflowTempletID) {
		WorkflowTempletID = workflowTempletID;
	}
	public String getDocType() {
		return DocType;
	}
	public void setDocType(String docType) {
		DocType = docType;
	}
	public String getBodyText() {
		return BodyText;
	}
	public void setBodyText(String bodyText) {
		BodyText = bodyText;
	}
	
	
}
