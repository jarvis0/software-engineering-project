package it.polimi.ingsw.ps23.model.actions;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.Council;

public class AssistantToElectCouncillor implements Action {

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
			game.getCurrentPlayer().updateAssistants(ASSISTANTS_COST);;
		} catch (InsufficientResourcesException e) {
			e.printStackTrace();
		}
		turnHandler.useQuickAction();		
	}

}
