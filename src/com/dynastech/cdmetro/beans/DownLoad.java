package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class DownLoad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// File file;//下载的文件
	String filename;
	String filepath;
	String iconurl;
	int filesize;// 文件大小
	int progress;// 下载进度
	int state;// 下载状态
	String taskid;// 任务的id
	String filetype;// file type

	public String getIconurl() {
		return iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public int getFilesize() {
		return filesize;
	}

	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}

	String time;// 下载的时间

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	// public File getFile() {
	// return file;
	// }
	//
	// public void setFile(File file) {
	// this.file = file;
	// }

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
