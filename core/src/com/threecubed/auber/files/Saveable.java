package com.threecubed.auber.files;

public interface Saveable {

	public SaveCategory getCategory();
	
	public String getSaveData();
	
	public void loadSaveData(String data);
	
}
