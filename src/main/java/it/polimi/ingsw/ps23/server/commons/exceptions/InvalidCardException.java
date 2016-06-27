package it.polimi.ingsw.ps23.server.commons.exceptions;

import java.io.IOException;

public class InvalidCardException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 706834637161580342L;
	private static final String EXCEPTION_TEXT = "You have selected an invalid card from your pool.";
	
	@Override
	public String toString() {
		return EXCEPTION_TEXT;
	}

}
