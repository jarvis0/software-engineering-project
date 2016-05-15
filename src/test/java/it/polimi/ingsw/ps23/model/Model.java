package it.polimi.ingsw.ps23.model;

import java.io.FileReader;
import java.util.Observable;

import com.opencsv.CSVReader;

public class Model extends Observable implements Cloneable {
	
	private int playerNumber;
	
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	public void inizializeGame() {
		CSVReader reader = new CSVReader(new FileReader("../../CSV/cities.csv"));
	}
}
