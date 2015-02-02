package com.dynastech.oa.beans;

import java.util.LinkedList;

public class UpdateBean {

	String Title;
	LinkedList<ProjectsBean> Projects;
	
	public LinkedList<ProjectsBean> getProjects() {
		return Projects;
	}
	public void setProjects(LinkedList<ProjectsBean> projects) {
		Projects = projects;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	
	
}
