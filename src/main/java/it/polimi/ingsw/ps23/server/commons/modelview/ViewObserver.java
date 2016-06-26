package it.polimi.ingsw.ps23.server.commons.modelview;

import it.polimi.ingsw.ps23.server.model.state.State;

/**
 * This interface is part of MVC - Observer/Observable pattern.
 * In particular, it defines the update methods that will be
 * called after a notify observers method in the relative 
 * observable class.
 * @author Alessandro Erba & Mirco Manzoni & Giuseppe Mascellaro
 *
 */
@FunctionalInterface
public interface ViewObserver {

	/**
	 * Receives and saves the new game model state to make the view show the
	 * right content to the user and returns immediately.
	 * @param state - new game state
	 */
	public void update(State state);
	
}
