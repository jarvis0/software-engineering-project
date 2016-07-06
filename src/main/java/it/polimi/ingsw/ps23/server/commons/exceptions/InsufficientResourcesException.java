package it.polimi.ingsw.ps23.server.commons.exceptions;

import java.io.IOException;
/**
 * IOexception that notify the players that the current {@link Player} hasn't got enough resources for an action.
 * @author Mirco Manzoni
 *
 */
public class InsufficientResourcesException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5674830133202712248L;
	private static final String EXCEPTION_STRING = "The current player hasn't got enough resources for this action";
	
	@Override
	public String toString() {
		return EXCEPTION_STRING;
	}

}
