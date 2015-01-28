package com.dynastech.cdmetro.beans;

import java.io.Serializable;
import java.util.LinkedList;

public class WorkFlowBean implements Serializable{

	private int id;
	private int ParentID;
	private String name;
	private LinkedList<WorkFlowBean> children;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentID() {
		return ParentID;
	}
	public void setParentID(int parentID) {
		ParentID = parentID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LinkedList<WorkFlowBean> getChildren() {
		return children;
	}
	public void setChildren(LinkedList<WorkFlowBean> children) {
		this.children = children;
	}
	
}
