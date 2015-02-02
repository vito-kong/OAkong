package com.dynastech.oa.ui.util;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.LinkedList;

import android.util.JsonReader;
import android.util.Log;

import com.dynastech.oa.beans.UnRead;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonUtils {
	/**
	 * json返回单个对象数据解析方法
	 * 
	 * @param jsonData
	 * @throws Exception
	 */
	public static <T> T parseUserFromJson(String jsonData, Class<T> entity)
			throws Exception {
		Gson gson = new Gson();
		T object = gson.fromJson(jsonData, entity);
		return object;
	}

	/**
	 * json返回单个对象数据解析方法
	 * 
	 * @param jsonData
	 * @throws Exception
	 */
	public static <T> T parseObjectFromJson(String jsonData, Class<T> entity)
			throws Exception{
		Gson gson = new Gson();
		T object = gson.fromJson(jsonData, entity);
		return object;
	}
	
	/**
	 * 多个对象json数据解析方法
	 * 
	 * @param jsonData
	 */
	public static <T> LinkedList<T> parseUserFromJsons(String jsonData,
			Type listType) {
		LinkedList<T> list = new LinkedList<T>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonData, listType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 多个对象json数据解析方法
	 * 
	 * @param jsonData
	 */
	public static <T> LinkedList<T> parseListsFromJsons(String jsonData,
			Type listType) throws Exception{
		LinkedList<T> list = new LinkedList<T>();
		try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonData, listType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 多个对象json数据解析方法
	 * 
	 * @param jsonData
	 */
	public static LinkedList<UnRead> parseUserFromJsons(String jsonData) {
		Type listType = new TypeToken<LinkedList<UnRead>>() {
		}.getType();
		Gson gson = new Gson();
		LinkedList<UnRead> list = gson.fromJson(jsonData, listType);
		UnRead unRead = list.get(0);
		Log.i("log", unRead.getText());
		return list;
	}


	public static String parseObejectToString(Object o) throws Exception{
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(o, new TypeToken<Object>() {}.getType());
	}
	//附件列表json解析
	public static <T> LinkedList<T> parseJson(String jsonData,Class<T> entity) {
        // 如果需要从解析json数据，首先要生成一个jsonReader对象
		LinkedList<T> lists=new LinkedList<T>();
		
        JsonReader reader = new JsonReader(new StringReader(jsonData));
        try {
            // 现在开始解析
            reader.beginArray();// 开始解析数组
            while (reader.hasNext()) {
                reader.beginObject();// 开始解析对象
                while (reader.hasNext()) {
                    String tagName = reader.nextName();
                    if (tagName.equals("name")) {
                        System.out.println(reader.nextString());
                    } else if (tagName.equals("age")) {
                        System.out.println(reader.nextInt());
                    }
                }
                reader.endObject();// 结束对象解析
            }
            reader.endArray();// 结束数组解析
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
    }


}
