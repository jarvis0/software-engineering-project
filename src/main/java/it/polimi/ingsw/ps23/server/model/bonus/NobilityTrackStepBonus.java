package it.polimi.ingsw.ps23.server.model.bonus;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class NobilityTrackStepBonus extends Bonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4715527573592795101L;

	public NobilityTrackStepBonus(String name) {
		super(name);
	}

	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) {
		game.getCurrentPlayer().updateNobilityPoints(getValue());
	}
	
}
