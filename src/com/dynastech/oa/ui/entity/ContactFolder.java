package com.dynastech.oa.ui.entity;


public class ContactFolder extends Entity {
	private String Id;
	private String Name;
	private String ParentId;
	private String Path;

	public String getId() {
		return this.Id;
	}

	public String getName() {
		return this.Name;
	}

	public String getParentId() {
		return this.ParentId;
	}

	public String getPath() {
		return this.Path;
	}

	public void setId(String paramString) {
		this.Id = paramString;
	}

	public void setName(String paramString) {
		this.Name = paramString;
	}

	public void setParentId(String paramString) {
		this.ParentId = paramString;
	}

	public void setPath(String paramString) {
		this.Path = paramString;
	}
}