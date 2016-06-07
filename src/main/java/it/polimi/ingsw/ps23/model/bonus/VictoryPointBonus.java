package it.polimi.ingsw.ps23.model.bonus;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;

public class VictoryPointBonus extends Bonus {

	public VictoryPointBonus(String name) {
		super(name);
	}
	
	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) {
		game.getCurrentPlayer().updateVictoryPoints(getValue());
	}
	
}
