package it.polimi.ingsw.ps23.model.bonus;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;

public abstract class Bonus implements Cloneable {

	private String id;
	private int value;
	
	public abstract void updateBonus(Player player) throws InsufficientResourcesException;

	public String getId() {
		return id;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
	      }
		return clone;
		}
	
}