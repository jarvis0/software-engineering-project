package it.polimi.ingsw.ps23.server.model.bonus;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Provides methods to create a specific bonus
 * @author Giuseppe Mascellaro
 *
 */
public abstract class Bonus implements Cloneable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4322173540984705455L;
	private final String name;
	
	Bonus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * @return true if the specified bonus object is a null bonus.
	 */
	public abstract boolean isNull();

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
	
}