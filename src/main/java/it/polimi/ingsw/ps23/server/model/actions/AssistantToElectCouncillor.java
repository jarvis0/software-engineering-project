package it.polimi.ingsw.ps23.server.model.actions;

import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncilException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncillorException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

/**
 * Provides methods to perform the specified game action if
 * the action is in a valid format.
 * @author Mirco Manzoni
 *
 */
public class AssistantToElectCouncillor extends ElectCouncillorAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 552188489180879350L;

	private static final int ASSISTANTS_COST = -1;

	/**
	 * Constructs all specified action parameters.
	 * @param councillor - free councillor the player want to elect
	 * @param council - the council the player wants to elect a councillor in
	 */
	public AssistantToElectCouncillor(String councillor, String council) {
		super(councillor, council);
	}
		
	@Override
	public void doAction(Game game, TurnHandler turnHandler) throws InvalidCouncillorException, InsufficientResourcesException, InvalidCouncilException {
		if(Math.abs(ASSISTANTS_COST) > game.getCurrentPlayer().getAssistants()) {
			throw new InsufficientResourcesException();
		}
		elect(game);
		game.getCurrentPlayer().updateAssistants(ASSISTANTS_COST);
		turnHandler.useQuickAction();		
	}

}
