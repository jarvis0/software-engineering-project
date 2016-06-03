package it.polimi.ingsw.ps23.model.bonus;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;

public class NobilityTrackStepBonus extends Bonus {
	
	public NobilityTrackStepBonus(String name) {
		super(name);
	}

	@Override
	public void updateBonus(Player player, TurnHandler turnHandler) throws InsufficientResourcesException {
		player.updateNobilityPoints(getValue(), turnHandler);
		
	}
	
}
