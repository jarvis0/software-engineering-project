package it.polimi.ingsw.ps23.server.commons.exceptions;

import java.io.IOException;

public class AlreadyBuiltHereException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9099831006835354232L;
	private static final String EXCEPTION_STRING = "You have already built here.";
	
	@Override
	public String toString() {
		return EXCEPTION_STRING;
	}

}
