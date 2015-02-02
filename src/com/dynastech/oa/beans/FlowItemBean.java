package com.dynastech.oa.beans;

public class FlowItemBean {

	private int ID;
	private String Title;
	private int WorkflowTempletID;
	private String DocType;
	
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
	
}
