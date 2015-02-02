package com.dynastech.oa.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainGson implements Serializable {

	private LinkedList<Data> Data;

	public LinkedList<Data> getData() {
		return Data;
	}

	public void setData(LinkedList<Data> data) {
		Data = data;
	}

}
