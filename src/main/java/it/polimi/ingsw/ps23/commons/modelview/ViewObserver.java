package it.polimi.ingsw.ps23.commons.modelview;

import it.polimi.ingsw.ps23.model.state.State;

public interface ViewObserver {
	   
	public abstract void update();
	public abstract void update(State state);
	
}
