package it.polimi.ingsw.ps23.server.model.bonus;

import java.io.Serializable;

@FunctionalInterface
public interface BonusSlot extends Serializable {

	public void addBonus(Bonus bonus);
	
}
