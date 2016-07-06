package it.polimi.ingsw.ps23.client.gui;

/**
 * Provides a Functional Interface for make GUI threads resume from a wait condition.
 * @author Giuseppe Mascellaro
 *
 */
@FunctionalInterface
public interface GUIView {

	/**
	 * Resume the specified thread which was in a wait condition.
	 */
	public void resume();
	
}
