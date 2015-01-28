package com.dynastech.cdmetro.beans;

import java.util.LinkedList;

public class ChildrenBean {

	private int id;
	private int ParentID;
	private String name;
	private LinkedList<ChildrenBean> children;
	
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
	public LinkedList<ChildrenBean> getChildren() {
		return children;
	}
	public void setChildren(LinkedList<ChildrenBean> children) {
		this.children = children;
	}
	
	
	
}
