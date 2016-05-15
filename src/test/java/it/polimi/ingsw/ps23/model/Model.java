package it.polimi.ingsw.ps23.model;

import java.util.Observable;

public class Model extends Observable implements Cloneable {
	
	private int playerNumber;
	
	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	public void inizializeGame() {		
	}
}
