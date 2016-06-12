package it.polimi.ingsw.ps23.model.actions;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;

public class AdditionalMainAction implements Action {

	private static final int ASSISTANTS_COST = -3;
	
	private Logger logger;
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		logger = Logger.getLogger(this.getClass().getName());
		try {
			game.getCurrentPlayer().updateAssistants(ASSISTANTS_COST);
		} catch (InsufficientResourcesException e) {
			logger.log(Level.SEVERE, "Insufficient current player assistants.", e);
		}
		turnHandler.addMainAction();
		turnHandler.useQuickAction();
	}

}
