package com.dynastech.oa.ui.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable{

	protected String cacheKey;

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
	
}
