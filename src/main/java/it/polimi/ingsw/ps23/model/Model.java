package it.polimi.ingsw.ps23.model;

import java.util.Observable;

public class Model extends Observable implements Cloneable {
	
	private int playersNumber;
	private Game game;
	
	public void setPlayersNumber(int playersNumber) {
		this.playersNumber = playersNumber;
	}

	public Game getGame() {
		game = new Game();
		return game;
	}
}