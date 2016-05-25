package it.polimi.ingsw.ps23.model;

import java.util.List;
import java.util.Observable;

public class Model extends Observable implements Cloneable {
	
	private int playersNumber;
	private List<String> playersID; 
	private Game game;
	
	public void setModel(List<String> playersID) {
		this.playersNumber = playersID.size();
		this.playersID = playersID;
		game = newGame();
		
	}

	public Game newGame() {
		game = new Game(playersNumber, playersID);
		return game;
	}
}