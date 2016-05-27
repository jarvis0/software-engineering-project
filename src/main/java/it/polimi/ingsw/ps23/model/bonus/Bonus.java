package it.polimi.ingsw.ps23.model.bonus;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;

public abstract class Bonus implements Cloneable {

	private String name;
	private int value;
	
	public Bonus(String name) {
		this.name = name;
	}
	
	public abstract void updateBonus(Player player) throws InsufficientResourcesException;

	public String getName() {
		return name;
	}
	
	protected int getValue() {
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
			e.printStackTrace();
	      }
		return clone;
	}
	
}