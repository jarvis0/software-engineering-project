package it.polimi.ingsw.ps23.server.model.bonus;

import javax.naming.InsufficientResourcesException;

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
	public void updateBonus(Game game, TurnHandler turnHandler) throws InsufficientResourcesException {
		game.getCurrentPlayer().updateNobilityPoints(getValue());
	}
	
}
