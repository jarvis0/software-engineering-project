package it.polimi.ingsw.ps23.server.model.state;

import java.util.logging.Level;
import java.util.logging.Logger;

abstract class ActionState implements State, Cloneable {

	private final String name;
	
	private Logger logger;
	
	ActionState(String name) {
		this.name = name;
		logger = Logger.getLogger(this.getClass().getName());
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
			logger.log(Level.SEVERE, "Cannot create action object.", e);
		}
		return clone;
	}
	
}
