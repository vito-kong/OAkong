package com.dynastech.oa.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import com.dynastech.oa.beans.Category;

import android.util.Log;
import android.util.Xml;

public class PULLPersonService {

	private static final String tag = "MainPage";

	public static List<Category> getCategorys(InputStream inStream)
			throws Exception {
		List<Category> categorys = null;
		Category category = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inStream, "UTF-8");
		int eventType = parser.getEventType();

		while (eventType != XmlPullParser.END_DOCUMENT) {

			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				categorys = new ArrayList<Category>();
				break;

			case XmlPullParser.START_TAG:

				String name = parser.getName();
				Log.i(tag, "name:" + name);
				if ("item".equals(name)) {
					category = new Category();
					// Log.i("MainPage", "key:"+parser.getAttributeValue(0));
					category.setKey(parser.getAttributeValue(0));
					// Log.i("MainPage", "value:"+parser.nextText());
					category.setValue(parser.nextText());
				}

				break;

			case XmlPullParser.END_TAG:
				if ("item".equals(parser.getName())) {
					categorys.add(category);
					category = null;
				}
				break;
			}
			eventType = parser.next();
		}
		return categorys;
	}
}
