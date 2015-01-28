package com.dynastech.cdmetro.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UploadInfo implements Serializable {// 上传器

	private static final long serialVersionUID = 1L;
	private List<Upload> upload = new ArrayList<Upload>();
	private String taskid;
	private String taskname;
	private String task_icon_url;

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public List<Upload> getUpload() {
		return upload;
	}

	public void setUpload(List<Upload> upload) {
		this.upload = upload;
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
