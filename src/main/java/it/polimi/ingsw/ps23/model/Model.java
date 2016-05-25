package it.polimi.ingsw.ps23.model;

import java.util.List;
import java.util.Observable;

public class Model extends Observable implements Cloneable {
	
	private List<String> playersID; 
	private Game game;
	
	public void setModel(List<String> playersID) {
		this.playersID = playersID;
		game = newGame();
	}

	public Game newGame() {
		try {
			game = new Game(playersID);
		} catch (NoCapitalException e) {
			e.printStackTrace();
		}
		return game;
	}
}