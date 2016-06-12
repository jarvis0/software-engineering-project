package it.polimi.ingsw.ps23.server.model.actions;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class EngageAnAssitant implements Action {

	private static final int COINS_COST = -3;
	private static final int EARNED_ASSITANTS = 1;
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		Logger logger = Logger.getLogger(this.getClass().getName());
		try {
			game.getCurrentPlayer().updateCoins(COINS_COST);
			game.getCurrentPlayer().updateAssistants(EARNED_ASSITANTS);
		} catch (InsufficientResourcesException e) {
			logger.log(Level.SEVERE, "Insufficient current player coins.", e);
		}
		turnHandler.useQuickAction();
	}

}
