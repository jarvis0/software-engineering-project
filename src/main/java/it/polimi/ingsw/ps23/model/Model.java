package it.polimi.ingsw.ps23.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Observable;

import com.opencsv.CSVReader;

public class Model extends Observable implements Cloneable {
	
	private int playersNumber;
	
	public void setPlayerNumber(int playersNumber) {
		this.playersNumber = playersNumber;
	}
	
	private List<String[]> parseCSVFile(String path) throws IOException {
		return new CSVReader(new FileReader(path)).readAll();
	}
	
	private void loadCities() {
		List<String[]> cities = null;
		try {
			cities = parseCSVFile("src/main/java/it/polimi/ingsw/ps23/CSV/cities.csv");
		} catch (IOException e) {
			System.out.println("Cannot load cities.");
		}
		for(String[] city : cities) {
			System.out.println(city[0]);
			//needed: link to City classd
		}
	}
	
	public void inizializeGame() {
		loadCities();
	}
}