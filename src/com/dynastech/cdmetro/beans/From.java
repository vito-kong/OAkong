package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class From implements Serializable {
	private String id;
	private String username;
	private String screen_name; // 姓名、昵稱
	private String profile_picture;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getScreenName() {
		return screen_name;
	}

	public void setScreenName(String name) {
		this.screen_name = name;
	}

	public String getProfile_picture() {
		return profile_picture;
	}

	public void setProfile_picture(String profile_picture) {
		this.profile_picture = profile_picture;
	}
}
