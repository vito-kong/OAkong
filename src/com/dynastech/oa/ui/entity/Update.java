package com.dynastech.oa.ui.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.dynastech.oa.AppException;
import com.dynastech.oa.beans.LzList;
import com.dynastech.oa.utils.JsonUtils;
import com.dynastech.oa.utils.StringUtils;
import com.google.gson.reflect.TypeToken;

import android.util.Xml;


public class Update implements Serializable{
	
	private String CustomName;
	private int Id;
	private String PlatformType;
	private String androidDownloadUrl;
	private String androidVersion;
	private String androidDescription;
	
	public String getCustomName() {
		return CustomName;
	}
	public void setCustomName(String customName) {
		CustomName = customName;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getPlatformType() {
		return PlatformType;
	}
	public void setPlatformType(String platformType) {
		PlatformType = platformType;
	}
	public String getAndroidDownloadUrl() {
		return androidDownloadUrl;
	}
	public void setAndroidDownloadUrl(String androidDownloadUrl) {
		this.androidDownloadUrl = androidDownloadUrl;
	}
	public String getAndroidVersion() {
		return androidVersion;
	}
	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}
	
	public static Update parse(String json) throws Exception {
		
		LinkedList<Update> updates=JsonUtils.parseListsFromJsons(json,
				new TypeToken<LinkedList<Update>>() {
				}.getType());
		if(updates!=null){
			for(Update update:updates){
				if("地铁OA".equals(update.CustomName)){
					return update;
				}
			}
		}
		return null;
		
	}
	public String getAndroidDescription() {
		return androidDescription;
	}
	public void setAndroidDescription(String androidDescription) {
		this.androidDescription = androidDescription;
	}
	
	
}
