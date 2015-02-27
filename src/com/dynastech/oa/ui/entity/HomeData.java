package com.dynastech.oa.ui.entity;

import java.io.IOException;
import java.util.LinkedList;

import android.util.Log;

import com.dynastech.oa.AppException;
import com.dynastech.oa.beans.Data;
import com.dynastech.oa.utils.JsonUtils;
import com.google.gson.reflect.TypeToken;

public class HomeData {

	private LinkedList<Data> data;
	private static HomeData home_data;
	
	public static HomeData get_HomeData() {
		// TODO Auto-generated constructor stub
		if(home_data==null){
			home_data=new HomeData();
		}
		return home_data;
	}
	
	public  HomeData  parse(String jsonData) throws Exception {
		
		Log.i("HomeData", "jsonData:"+jsonData);
		data=JsonUtils.parseListsFromJsons(jsonData,
				new TypeToken<LinkedList<Data>>() {
				}.getType());
		setData(data);
		return this;
		
	}

	public LinkedList<Data> getData() {
		return data;
	}

	public void setData(LinkedList<Data> data) {
		this.data = data;
	}

	
	
	



}
