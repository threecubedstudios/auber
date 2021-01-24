package com.threecubed.auber.files;

public interface Saveable {

	public String getCategory();
	
	public String getSaveData();
	
	public void loadSaveData(String data);
	
}
