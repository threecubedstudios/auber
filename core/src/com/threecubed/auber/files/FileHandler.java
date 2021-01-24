package com.threecubed.auber.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

	private static final List<Saveable> saveables = new ArrayList<Saveable>();

	private static final String path = "saves/";

	public static void addSaveable(Saveable saveable) {
		saveables.add(saveable);
	}

	public static void save(String savename) throws IOException {
		if (!new File(path).exists()) {
			new File(path).mkdir();
		}

		File saveFile = new File(path + savename);
		saveFile.createNewFile();

		BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));

		for (Saveable saveable : saveables) {
			writer.write(saveable.getCategory() + "\n");
			writer.write(saveable.getSaveData() + "\n");
		}

		writer.close();

	}

}
