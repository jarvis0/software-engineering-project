package it.polimi.ingsw.ps23.server.commons.modelview;

import it.polimi.ingsw.ps23.server.model.state.State;

@FunctionalInterface
public interface ViewObserver {

	public void update(State state);
	
}
