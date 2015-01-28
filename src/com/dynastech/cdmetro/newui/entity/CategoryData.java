package com.dynastech.cdmetro.newui.entity;

import java.io.IOException;
import java.util.LinkedList;

import android.util.Log;

import com.dynastech.cdmetro.AppException;
import com.dynastech.cdmetro.beans.LzList;
import com.dynastech.cdmetro.newui.util.JsonUtils;
import com.google.gson.reflect.TypeToken;

public class CategoryData extends Entity{

	private LinkedList<LzList> data;
	private static CategoryData category_data;
	
	public static CategoryData get_CategoryData(){
		if(category_data==null){
			category_data=new CategoryData();
		}
		return category_data;
	}
	public  CategoryData parse(String jsonData) 
			throws Exception {
		Log.i("CategoryData", "jsonData:"+jsonData);
		data=JsonUtils.parseListsFromJsons(jsonData,
				new TypeToken<LinkedList<LzList>>() {
				}.getType());
		setData(data);
		return this;
		
	}

	public LinkedList<LzList> getData() {
		return data;
	}

	public void setData(LinkedList<LzList> data) {
		this.data = data;
	}
	
}
