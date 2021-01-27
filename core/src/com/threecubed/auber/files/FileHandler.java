package com.threecubed.auber.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import com.threecubed.auber.AuberGame;
import com.threecubed.auber.screens.GameScreen;

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
			writer.write(saveable.getCategory().toString() + "\n");
			writer.write(saveable.getSaveData() + "\n");
		}

		writer.close();

	}

	public static void load(final AuberGame game) throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/" + path));
		fileChooser.showOpenDialog(null);

		game.setScreen(new GameScreen(game, false));
		FileHandler.load(fileChooser.getSelectedFile());
	}

	private static List<Saveable> load(File saveFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(saveFile));
		String line = reader.readLine();

		int[] amount = new int[SaveCategory.values().length];

		while (line != null && !line.equalsIgnoreCase("")) {
			SaveCategory category = SaveCategory.valueOf(line);
			line = reader.readLine();

			int index = category.getIndex();
			int ctr = 0;

			for (Saveable saveable : saveables) {
				if (saveable.getCategory().equals(category)) {
					if (ctr == amount[index]) {
						saveable.loadSaveData(line);
						break;
					}

					ctr += 1;
				}
			}

			amount[index] = amount[index] + 1;

			line = reader.readLine();
		}

		reader.close();

		return saveables;
	}

}
