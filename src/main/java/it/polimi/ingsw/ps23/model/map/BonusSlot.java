package it.polimi.ingsw.ps23.model.map;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

@FunctionalInterface
public interface BonusSlot {
	
	@Override
	public String toString();

	public void addBonus(Bonus bonus);
}
