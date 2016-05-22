package it.polimi.ingsw.ps23.model.bonus;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;

public abstract class Bonus {
	
	private int value;
	
	public abstract void updateBonus(Player player) throws InsufficientResourcesException;

	protected int getValue() {
		return value;
	}
}