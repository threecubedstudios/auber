package com.threecubed.auber.files;

public enum SaveCategory {

	ENTITY, PLAYER, INFILTRATOR, WORLD;

	public int getIndex() {
		for (int i = 0; i < values().length; i++) {
			if (values()[i].equals(this)) {
				return i;
			}
		}
		
		return -1;
	}

}
