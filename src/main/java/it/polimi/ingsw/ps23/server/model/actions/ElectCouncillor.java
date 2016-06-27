package it.polimi.ingsw.ps23.server.model.actions;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncilException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncillorException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class ElectCouncillor extends ElectCouncillorAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8467179646741897111L;

	private static final int EARNED_COINS = 4;
	
	
		
	public ElectCouncillor(String councillor, String council) {
		super(councillor, council);
	}
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) throws InvalidCouncillorException, InvalidCouncilException {
		elect(game);
		game.getCurrentPlayer().updateCoins(EARNED_COINS);
		turnHandler.useMainAction();
	}
	
}
