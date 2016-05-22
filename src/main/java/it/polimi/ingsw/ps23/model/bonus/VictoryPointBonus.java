package it.polimi.ingsw.ps23.model.bonus;

import it.polimi.ingsw.ps23.model.Player;

public class VictoryPointBonus extends Bonus {

	public VictoryPointBonus(String id) {
		super(id);
	}
	
	@Override
	public void updateBonus(Player player) {
		player.updateVictoryPoints(getValue());
	}
}
