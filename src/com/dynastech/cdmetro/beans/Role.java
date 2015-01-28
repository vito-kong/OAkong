package com.dynastech.cdmetro.beans;

import java.io.Serializable;
import java.util.LinkedList;

public class Role implements Serializable {

	private String Title;
	private String Guid;
	private String Name;
	private String Type;
	private LinkedList<Orignators> Users;

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getGuid() {
		return Guid;
	}

	public void setGuid(String guid) {
		Guid = guid;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public LinkedList<Orignators> getUsers() {
		return Users;
	}

	public void setUsers(LinkedList<Orignators> users) {
		Users = users;
	}

}
