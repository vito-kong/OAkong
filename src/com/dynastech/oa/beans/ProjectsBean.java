package com.dynastech.oa.beans;

import java.util.LinkedList;

public class ProjectsBean {

	String ProjectName;
	LinkedList<VersionsBean>Versions;
	
	public String getProjectName() {
		return ProjectName;
	}
	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}
	public LinkedList<VersionsBean> getVersions() {
		return Versions;
	}
	public void setVersions(LinkedList<VersionsBean> versions) {
		Versions = versions;
	}
}
