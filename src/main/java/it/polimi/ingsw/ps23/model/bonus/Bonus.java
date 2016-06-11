package it.polimi.ingsw.ps23.model.bonus;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;

public abstract class Bonus implements Cloneable {

	private String name;
	private int value;
	
	private Logger logger;
	
	public Bonus(String name) {
		this.name = name;
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	public abstract void updateBonus(Game game, TurnHandler turnHandler) throws InsufficientResourcesException;

	public String getName() {
		return name;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return name + " " + value;
	}
	
	@Override
	protected Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			logger.log(Level.SEVERE, "Cannot create bonus object.", e);
		}
		return clone;
	}
	
}