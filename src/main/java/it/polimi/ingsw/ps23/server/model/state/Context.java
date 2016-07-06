package it.polimi.ingsw.ps23.server.model.state;

import java.io.Serializable;
/**
 * Provides methods to manage the selected {@link State}.
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public class Context implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1116178207589642489L;
	private State state;
	/**
	 * Initialize the state at the default value
	 */
	public Context() {
		state = null;
	}
	
	void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	/**
	 * Adds the exception that occurred in the last {@link State} to the current {@link State}.
	 * @param e - the exception occurred
	 */
	public void addExceptionText(Exception e) {
		state.setExceptionString(e.toString());
		
	}
}
