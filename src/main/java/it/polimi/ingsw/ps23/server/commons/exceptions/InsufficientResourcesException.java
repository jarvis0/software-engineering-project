package it.polimi.ingsw.ps23.server.commons.exceptions;

import java.io.IOException;

public class InsufficientResourcesException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5674830133202712248L;
	private static final String EXCEPTION_STRING = "You haven't got enough resources for this action";
	
	@Override
	public String toString() {
		return EXCEPTION_STRING;
	}

}
