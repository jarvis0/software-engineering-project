package it.polimi.ingsw.ps23.server.model.actions;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;

public class AssistantToElectCouncillor implements Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 552188489180879350L;

	private static final int ASSISTANTS_COST = -1;
	
	private String councillor;
	private Council council;
	
	public AssistantToElectCouncillor(String councillor, Council council) {
		this.councillor = councillor;
		this.council = council;
	}
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		game.getFreeCouncillors().electCouncillor(councillor, council);
		try {
			game.getCurrentPlayer().updateAssistants(ASSISTANTS_COST);
		} catch (InsufficientResourcesException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Insufficent current player assistants.", e);
		}
		turnHandler.useQuickAction();		
	}

}
