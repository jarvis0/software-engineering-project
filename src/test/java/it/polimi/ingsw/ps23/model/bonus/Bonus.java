package it.polimi.ingsw.ps23.model.bonus;

import it.polimi.ingsw.ps23.model.Player;

public abstract class Bonus {
	
	protected int value;
	
	public abstract void updateBonus(Player player);

}
