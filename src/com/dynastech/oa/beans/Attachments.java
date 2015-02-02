package com.dynastech.oa.beans;

public class Attachments {

	String Title;
	boolean AllowOnlineRead;
	String MobileDownloadUrl;
	String MobileReadUrl;
	String IconUrl;
	String Created;
	int ContentLength;
	String FileSize;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public boolean isAllowOnlineRead() {
		return AllowOnlineRead;
	}

	public void setAllowOnlineRead(boolean allowOnlineRead) {
		AllowOnlineRead = allowOnlineRead;
	}

	public String getMobileDownloadUrl() {
		return MobileDownloadUrl;
	}

	public void setMobileDownloadUrl(String mobileDownloadUrl) {
		MobileDownloadUrl = mobileDownloadUrl;
	}

	public String getMobileReadUrl() {
		return MobileReadUrl;
	}

	public void setMobileReadUrl(String mobileReadUrl) {
		MobileReadUrl = mobileReadUrl;
	}

	public String getIconUrl() {
		return IconUrl;
	}

	public void setIconUrl(String iconUrl) {
		IconUrl = iconUrl;
	}

	public String getCreated() {
		return Created;
	}

	public void setCreated(String created) {
		Created = created;
	}

	public String getFileSize() {
		return FileSize;
	}

	public void setFileSize(String fileSize) {
		FileSize = fileSize;
	}

}
