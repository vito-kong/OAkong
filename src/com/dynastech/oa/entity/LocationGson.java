package com.dynastech.oa.entity;

import com.dynastech.cdmetro.newui.entity.Entity;

public class LocationGson extends Entity{
	private double accuracy;
	private String baiduX;
	private String baiduY;
	private int personId;
	private String text;
	private String time;

	public double getAccuracy() {
		return this.accuracy;
	}

	public String getBaiduX() {
		return this.baiduX;
	}

	public String getBaiduY() {
		return this.baiduY;
	}

	public int getPersonId() {
		return this.personId;
	}

	public String getText() {
		return this.text;
	}

	public String getTime() {
		return this.time;
	}

	public void setAccuracy(double paramDouble) {
		this.accuracy = paramDouble;
	}

	public void setBaiduX(String paramString) {
		this.baiduX = paramString;
	}

	public void setBaiduY(String paramString) {
		this.baiduY = paramString;
	}

	public void setPersonId(int paramInt) {
		this.personId = paramInt;
	}

	public void setText(String paramString) {
		this.text = paramString;
	}

	public void setTime(String paramString) {
		this.time = paramString;
	}
}

/*
 * Location: D:\dex2jar-0.0.9.15\classes_dex2jar.jar Qualified Name:
 * com.dynastech.cdmetro.newui.entity.LocationGson JD-Core Version: 0.6.0
 */