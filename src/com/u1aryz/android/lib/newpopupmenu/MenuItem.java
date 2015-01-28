package com.u1aryz.android.lib.newpopupmenu;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class MenuItem {

	private int itemId;
	private String title;
	private Drawable icon;
	private Intent intent;
	private PopupMenu popmenu;
	
	
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public Intent getIntent() {
		return intent;
	}

	public PopupMenu getPopmenu() {
		return popmenu;
	}

	public void setPopmenu(PopupMenu popmenu) {
		this.popmenu = popmenu;
	}
}
