package it.polimi.ingsw.ps23.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.opencsv.CSVReader;

public class Model extends Observable implements Cloneable {
	
	private int playerNumber;
	
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	private List<String[]> parseCSVFile(String path) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path));
		return reader.readAll();
	}
	public void inizializeGame() {
		try {
			List<String[]> cities = parseCSVFile("src/test/java/it/polimi/ingsw/ps23/CSV/cities.csv");
			for(String[] temp : cities) {
				System.out.println(temp[0]);
				//needed: link to City class
			}
		} catch (IOException e) {
			System.out.println("Cannot load cities.");
		}
	}
}