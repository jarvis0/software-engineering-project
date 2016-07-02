package it.polimi.ingsw.ps23.server.model.state;

import java.io.Serializable;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;
/**
 * Provide methods to change current state and start the correct visit in {@link ViewVisitor}.
 * @author Alessandro Erba, Giuseppe Mascellaro, Mirco Manzoni
 *
 */
public abstract class State implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3883706184904910069L;
	
	private String exceptionString = new String();
	/**
	 * Change the current state in this state. The state will take all the information to work.
	 * @param context - the container of the new state
	 * @param game - permit to the state to take all the necessary info
	 */
	public abstract void changeState(Context context, Game game);
	/**
	 * Start the visit of this state.
	 * @param view - where the visit take place
	 */
	public abstract void acceptView(ViewVisitor view);
	
	public void setExceptionString(String exceptionString) {
		this.exceptionString = exceptionString;
	}
	
	public String getExceptionString() {
		return exceptionString;
	}
	/**
	 * Calculate if some exception occurred during the last state.
	 * @return true if are present exception, false if not.
	 */
	public boolean arePresentException() {
		return exceptionString.length() != 0;
	}
	
}
