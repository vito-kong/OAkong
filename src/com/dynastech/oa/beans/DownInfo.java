package com.dynastech.oa.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DownInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DownLoad> downloader = new ArrayList<DownLoad>();
	private String taskid;
	private String taskname;
	private String task_icon_url;

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public List<DownLoad> getDownloader() {
		return downloader;
	}

	public void setDownloader(List<DownLoad> downloader) {
		this.downloader = downloader;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getTask_icon_url() {
		return task_icon_url;
	}

	public void setTask_icon_url(String task_icon_url) {
		this.task_icon_url = task_icon_url;
	}

}
