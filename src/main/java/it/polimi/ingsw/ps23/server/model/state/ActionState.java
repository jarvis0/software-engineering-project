package it.polimi.ingsw.ps23.server.model.state;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.commons.exceptions.IllegalActionSelectedException;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
/**
 * Provides methods to manage all the state in with the user have to perform an action.
 * @author Mirco Manzoni
 *
 */
public abstract class ActionState extends State implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7791957934222521528L;
	private final String name;
	
	ActionState(String name) {
		this.name = name;
	}
	
	String getName() {
		return name;
	}
	/**
	 * Calculate if the current {@link Player} can perform a specific {@link Action}.
	 * @param turnHandler - current turn handler to check if there are available actions.
	 * @throws IllegalActionSelectedException if the user try yo perform an invalid action.
	 */
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
