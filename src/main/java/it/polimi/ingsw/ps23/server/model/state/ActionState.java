package it.polimi.ingsw.ps23.server.model.state;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class ActionState extends State implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7924914661050611016L;

	private final String name;
	
	ActionState(String name) {
		this.name = name;
	}
	
	String getName() {
		return name;
	}
	
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
