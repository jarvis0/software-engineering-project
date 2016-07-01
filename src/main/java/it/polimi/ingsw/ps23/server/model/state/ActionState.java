package it.polimi.ingsw.ps23.server.model.state;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.commons.exceptions.IllegalActionSelectedException;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

@SuppressWarnings("serial")
public abstract class ActionState extends State implements Cloneable, Serializable {

	private final String name;
	
	ActionState(String name) {
		this.name = name;
	}
	
	String getName() {
		return name;
	}
	
	public abstract void canPerformThisAction(TurnHandler turnHandler) throws IllegalActionSelectedException;
	
	@Override
	protected Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot create action object.", e);
		}
		return clone;
	}
	
}
