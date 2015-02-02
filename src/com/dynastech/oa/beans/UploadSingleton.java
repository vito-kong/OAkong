package com.dynastech.oa.beans;

import java.util.HashMap;

public class UploadSingleton {

	private HashMap<String, UploadInfo> infos = new HashMap<String, UploadInfo>();
	private HashMap<String, DownInfo> d_infos = new HashMap<String, DownInfo>();

	private static UploadSingleton singleton;

	public static UploadSingleton getUploadSingleton() {
		if (singleton == null) {
			singleton = new UploadSingleton();
		}
		return singleton;
	}

	public void clearSingleton() {
		singleton = null;
	};

	public HashMap<String, UploadInfo> getInfos() {
		return infos;
	}

	public void setInfos(HashMap<String, UploadInfo> infos) {
		this.infos = infos;
	}

	public HashMap<String, DownInfo> getD_infos() {
		return d_infos;
	}

	public void setD_infos(HashMap<String, DownInfo> d_infos) {
		this.d_infos = d_infos;
	}
}
