package it.polimi.ingsw.ps23.server.model.state;

import it.polimi.ingsw.ps23.server.commons.exceptions.IllegalActionSelectedException;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

abstract class MainActionState extends ActionState {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9031311579386894094L;

	MainActionState(String name) {
		super(name);
	}
	
	public void canPerformThisAction(TurnHandler turnHandler) throws IllegalActionSelectedException {
		if (!turnHandler.isAvailableMainAction()) {
			throw new IllegalActionSelectedException();
		}
	}

}
