package it.polimi.ingsw.ps23.model.bonus;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;

public class VictoryPointBonus extends Bonus {

	public VictoryPointBonus(String name) {
		super(name);
	}
	
	@Override
	public void updateBonus(Player player, TurnHandler turnHandler) {
		player.updateVictoryPoints(getValue());
	}
}
