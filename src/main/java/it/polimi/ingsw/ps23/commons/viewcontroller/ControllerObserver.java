package it.polimi.ingsw.ps23.commons.viewcontroller;

import java.util.List;

import it.polimi.ingsw.ps23.model.actions.Action;
import it.polimi.ingsw.ps23.model.state.State;

public interface ControllerObserver {

	public abstract void update();
	public abstract void update(List<String> state);
	public abstract void update(State state);
	public abstract void update(Action action);
	
}
