package com.dynastech.cdmetro.beans;

public class FileBean {
	private Integer id;// 文件id
	private int size;// 文件大小
	private String name;// 文件名称
	private int state;// 文件状态
	private String path;// 文件路径
	private int downloadsize;// 文件下载长度

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getDownloadsize() {
		return downloadsize;
	}

	public void setDownloadsize(int downloadsize) {
		this.downloadsize = downloadsize;
	}

}
