package it.polimi.ingsw.ps23.commons.viewcontroller;

import java.util.List;

public interface ControllerObserver {

	public abstract void update(List<String> state);
	
}
