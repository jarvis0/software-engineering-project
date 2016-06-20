package it.polimi.ingsw.ps23.server.model.bonus;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public abstract class Bonus implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4322173540984705455L;
	private final String name;
	private int value;
	
	Bonus(String name) {
		this.name = name;
	}
	
	public abstract void updateBonus(Game game, TurnHandler turnHandler);

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
	protected Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot create bonus object.", e);
		}
		return clone;
	}
	
	@Override
	public String toString() {
		if(!(this instanceof NullBonus)) {
			return name + " " + value;
		}
		else {
			return " - ";
		}
	}
	
}