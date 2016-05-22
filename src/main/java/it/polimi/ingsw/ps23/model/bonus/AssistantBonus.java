package it.polimi.ingsw.ps23.model.bonus;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;

public class AssistantBonus extends Bonus {

	public AssistantBonus() {
		super.setId("assistant");
	}
	
	@Override
	public void updateBonus(Player player) throws InsufficientResourcesException {
		player.updateAssistants(getValue());
	}	
}
