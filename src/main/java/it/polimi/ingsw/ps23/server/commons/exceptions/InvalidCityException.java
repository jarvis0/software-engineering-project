package it.polimi.ingsw.ps23.server.commons.exceptions;

import java.io.IOException;
/**
 * IOexception that notify the players that the current {@link Player} have selected an invalid city.
 * @author Mirco Manzoni
 *
 */
public class InvalidCityException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7745659014979461534L;
	private static final String EXCEPTION_STRING = "The current player has selected an illegal city.";
	
	@Override
	public String toString() {
		return EXCEPTION_STRING;
	}


}
