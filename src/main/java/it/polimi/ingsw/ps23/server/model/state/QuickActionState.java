package it.polimi.ingsw.ps23.server.model.state;

import it.polimi.ingsw.ps23.server.commons.exceptions.IllegalActionSelectedException;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

abstract class QuickActionState extends ActionState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1454397467441162084L;

	QuickActionState(String name) {
		super(name);
	}

	public void canPerformThisAction(TurnHandler turnHandler) throws IllegalActionSelectedException {
		if (!turnHandler.isAvailableQuickAction()) {
			throw new IllegalActionSelectedException();
		}
	}
	
}
