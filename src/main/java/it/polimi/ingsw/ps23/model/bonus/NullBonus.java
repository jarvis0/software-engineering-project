package it.polimi.ingsw.ps23.model.bonus;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;

public class NullBonus extends Bonus {
	
	public NullBonus(String name) {
		super(name);
	}

	@Override
	public void updateBonus(Player player) throws InsufficientResourcesException {
		// TODO Auto-generated method stub
		
	}
	
}
