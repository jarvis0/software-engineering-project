package it.polimi.ingsw.ps23.commons.modelview;

import it.polimi.ingsw.ps23.model.Game;

public interface ViewObserver {
	   
	public abstract void update();
	public abstract void update(Game game);
	
}
