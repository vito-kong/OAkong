package com.dynastech.oa.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.dynastech.oa.beans.Category;

public class SAXPersonService {

	public List<Category> getCategory(InputStream inStream) throws Throwable {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		CategoryParser categoryParser = new CategoryParser();
		parser.parse(inStream, categoryParser);
		inStream.close();
		return categoryParser.getCategorys();
	}

	private final class CategoryParser extends DefaultHandler {
		private List<Category> categorys = null;
		private String tag = null;
		private Category category = null;

		public List<Category> getCategorys() {
			return categorys;
		}

		@Override
		public void startDocument() throws SAXException {
			categorys = new ArrayList<Category>();
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			if ("item".equals(localName)) {
				category = new Category();
				category.setKey(attributes.getValue(0));
				category.setTitle(attributes.getValue(1));
			}
			tag = localName;
		}

		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if (tag != null) {
				String data = new String(ch, start, length);
				if ("item".equals(tag)) {
					category.setValue(data);
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if ("item".equals(localName)) {
				categorys.add(category);
				category = null;
			}
			tag = null;
		}
	}
}
