package it.polimi.ingsw.ps23.server.model.actions;

import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class AdditionalMainAction implements Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5396506198911022286L;

	private static final int ASSISTANTS_COST = -3;
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) throws InsufficientResourcesException {
		if(Math.abs(ASSISTANTS_COST) > game.getCurrentPlayer().getAssistants()) {
			throw new InsufficientResourcesException();
		}
		game.getCurrentPlayer().updateAssistants(ASSISTANTS_COST);
		turnHandler.addMainAction();
		turnHandler.useQuickAction();
	}

}
