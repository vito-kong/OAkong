package com.dynastech.oa.beans;

import java.util.LinkedList;

public class FlowContentBean {

	private int page;
	private int totle;
	private LinkedList<RowsBean> rows;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotle() {
		return totle;
	}
	public void setTotle(int totle) {
		this.totle = totle;
	}
	public LinkedList<RowsBean> getRows() {
		return rows;
	}
	public void setRows(LinkedList<RowsBean> rows) {
		this.rows = rows;
	}
	
}
