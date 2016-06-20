package it.polimi.ingsw.ps23.server.model.state;

import java.io.Serializable;

public class Context implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1116178207589642489L;
	private State state;
	
	public Context() {
		state = null;
	}
	
	void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
}
