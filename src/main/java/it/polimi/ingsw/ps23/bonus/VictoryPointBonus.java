package it.polimi.ingsw.ps23.bonus;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class VictoryPointBonus extends Bonus {

	public VictoryPointBonus(String name) {
		super(name);
	}
	
	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) {
		game.getCurrentPlayer().updateVictoryPoints(getValue());
	}
	
}
