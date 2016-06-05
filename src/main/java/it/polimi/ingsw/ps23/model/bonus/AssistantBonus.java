package it.polimi.ingsw.ps23.model.bonus;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;

public class AssistantBonus extends Bonus {

	public AssistantBonus(String name) {
		super(name);
	}
	
	@Override
	public void updateBonus(Player player, TurnHandler turnHandler) throws InsufficientResourcesException {
		player.updateAssistants(getValue());
	}

	@Override
	public void updateBonusReward(Player player) throws InsufficientResourcesException {
		player.updateAssistants(getValue());
	}	
}
