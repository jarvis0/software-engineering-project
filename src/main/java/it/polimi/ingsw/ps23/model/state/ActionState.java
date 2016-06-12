package it.polimi.ingsw.ps23.model.state;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ActionState implements State, Cloneable {

	private String name;
	
	private Logger logger;
	
	public ActionState(String name) {
		this.name = name;
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	public String getName() {
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
