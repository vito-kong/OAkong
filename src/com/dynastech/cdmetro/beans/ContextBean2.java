package com.dynastech.cdmetro.beans;

import java.io.Serializable;

public class ContextBean2 implements Serializable{

	private String filter;
	private String orderBy;
	
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
}
