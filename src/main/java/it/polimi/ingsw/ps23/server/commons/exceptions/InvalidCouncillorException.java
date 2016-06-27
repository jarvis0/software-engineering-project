package it.polimi.ingsw.ps23.server.commons.exceptions;

import java.io.IOException;

public class InvalidCouncillorException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4109956462010705543L;
	private static final String EXCEPTION_STRING = "You have selected an illegal councillor from the pool.";
	
	@Override
	public String toString() {
		return EXCEPTION_STRING;
	}
}
