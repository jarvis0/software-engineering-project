package it.polimi.ingsw.ps23.server.model.actions;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class EngageAnAssitant implements Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 158667005620083255L;
	private static final int COINS_COST = -3;
	private static final int EARNED_ASSITANTS = 1;
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) throws InsufficientResourcesException {
		if(Math.abs(COINS_COST) > game.getCurrentPlayer().getCoins()) {
			throw new InsufficientResourcesException();
		}
		game.getCurrentPlayer().updateCoins(COINS_COST);
		game.getCurrentPlayer().updateAssistants(EARNED_ASSITANTS);
		turnHandler.useQuickAction();
	}

}
