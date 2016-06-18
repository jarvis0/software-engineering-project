package it.polimi.ingsw.ps23.server.model.actions;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class AdditionalMainAction implements Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5396506198911022286L;

	private static final int ASSISTANTS_COST = -3;
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		try {
			game.getCurrentPlayer().updateAssistants(ASSISTANTS_COST);
		} catch (InsufficientResourcesException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Insufficient current player assistants.", e);
		}
		turnHandler.addMainAction();
		turnHandler.useQuickAction();
	}

}
