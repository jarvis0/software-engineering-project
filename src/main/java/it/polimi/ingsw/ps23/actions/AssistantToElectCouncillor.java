package it.polimi.ingsw.ps23.actions;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class AssistantToElectCouncillor implements Action {

	private static final int ASSISTANTS_COST = -1;
	
	private String councillor;
	private Council council;
	
	private Logger logger;
	
	public AssistantToElectCouncillor(String councillor, Council council) {
		this.councillor = councillor;
		this.council = council;
	}
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		logger = Logger.getLogger(this.getClass().getName());
		game.getFreeCouncillors().electCouncillor(councillor, council);
		try {
			game.getCurrentPlayer().updateAssistants(ASSISTANTS_COST);
		} catch (InsufficientResourcesException e) {
			logger.log(Level.SEVERE, "Insufficent current player assistants.", e);
		}
		turnHandler.useQuickAction();		
	}

}
