package it.polimi.ingsw.ps23.commons.viewcontroller;

import java.util.List;

import it.polimi.ingsw.ps23.model.state.Context;

public interface ControllerObserver {

	public abstract void update();
	public abstract void update(List<String> state);
	public abstract void update(Context context);
	
}
