package it.polimi.ingsw.ps23.model.bonus;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;

public abstract class Bonus implements Cloneable {

	private String id;
	private int value;
	
	public Bonus(String id) {
		this.id = id;
	}
	
	public abstract void updateBonus(Player player) throws InsufficientResourcesException;

	public String getId() {
		return id;
	}
	
	protected int getValue() {
		return value;
	}
	
	void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return id + " " + value;
	}
	
	@Override
	protected Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
	      }
		return clone;
	}
	
}