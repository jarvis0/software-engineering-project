package it.polimi.ingsw.ps23.server.model.actions;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncilException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncillorException;
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
	
	private void checkAction() throws InvalidCouncilException {
		if(council == null) {
			throw new InvalidCouncilException();
		}
	}
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) throws InvalidCouncillorException, InsufficientResourcesException, InvalidCouncilException {
		checkAction();
		game.getFreeCouncillors().electCouncillor(councillor, council);
		if(Math.abs(ASSISTANTS_COST) > game.getCurrentPlayer().getAssistants()) {
			throw new InsufficientResourcesException();
		}
		game.getCurrentPlayer().updateAssistants(ASSISTANTS_COST);
		turnHandler.useQuickAction();		
	}

}
