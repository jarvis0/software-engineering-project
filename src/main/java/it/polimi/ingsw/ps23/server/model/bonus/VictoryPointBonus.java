package it.polimi.ingsw.ps23.server.model.bonus;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class VictoryPointBonus extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7580304211077512707L;

	public VictoryPointBonus(String name) {
		super(name);
	}
	
	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) {
		game.getCurrentPlayer().updateVictoryPoints(getValue());
	}
	
}
