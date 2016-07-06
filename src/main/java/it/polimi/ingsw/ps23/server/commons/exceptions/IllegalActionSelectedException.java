package it.polimi.ingsw.ps23.server.commons.exceptions;

import java.io.IOException;
/**
 * IOexception that notify the players that the current {@link Player} have selected an illegal action.
 * @author Mirco Manzoni
 *
 */
public class IllegalActionSelectedException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5633750940150851188L;
	private static final String EXCEPTION_STRING = "The current player cannot perform this action.";
	
	@Override
	public String toString() {
		return EXCEPTION_STRING;
	}

}
