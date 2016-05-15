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
	private List parseCSVFile(String path) {
			CSVReader reader;
			try {
				reader = new CSVReader(new FileReader(path));
				ArrayList<String[]> a = (ArrayList<String[]>) reader.readAll();
				System.out.println(a.);
			} catch (IOException e) {
				System.out.println("Cannot load cities.");
			}
			return null;
	}
	public void inizializeGame() {
		List cities = parseCSVFile("src/test/java/it/polimi/ingsw/ps23/CSV/cities.csv");
		
	}
}
