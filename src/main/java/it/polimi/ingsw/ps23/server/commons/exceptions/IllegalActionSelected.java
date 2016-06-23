package it.polimi.ingsw.ps23.server.commons.exceptions;

import java.io.IOException;

public class IllegalActionSelected extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5633750940150851188L;
	private static final String EXCEPTION_STRING = "You have selected an illegal action.";
	
	@Override
	public String toString() {
		return EXCEPTION_STRING;
	}

}
