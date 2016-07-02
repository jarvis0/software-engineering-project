package it.polimi.ingsw.ps23.server.commons.exceptions;

import java.io.IOException;
/**
 * IOexception that notify the players that the current {@link Player} have selected an invalid council.
 * @author Mirco Manzoni
 *
 */
public class InvalidCouncilException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3949419734949149729L;
	private static final String EXCEPTION_STRING = "You have selected an illegal council.";
	
	@Override
	public String toString() {
		return EXCEPTION_STRING;
	}
}
