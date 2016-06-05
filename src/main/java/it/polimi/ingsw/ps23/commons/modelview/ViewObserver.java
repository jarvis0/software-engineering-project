package it.polimi.ingsw.ps23.commons.modelview;

import it.polimi.ingsw.ps23.model.state.State;

@FunctionalInterface
public interface ViewObserver {

	public abstract void update(State state);
	
}
