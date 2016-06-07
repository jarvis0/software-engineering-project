package it.polimi.ingsw.ps23.model.bonus;

import java.util.List;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;

public class NullBonus extends Bonus {
	
	public NullBonus(String name) {
		super(name);
	}

	@Override
	public void updateBonus(Player player, TurnHandler turnHandler, List<Bonus> superBonus) throws InsufficientResourcesException {
				
	}

	@Override
	public void updateBonusReward(Player player) throws InsufficientResourcesException {
		
	}
	
}
