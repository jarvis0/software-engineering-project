package it.polimi.ingsw.ps23.server.commons.exceptions;

import java.io.IOException;

public class InvalidNumberOfAssistantException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -932183545744004879L;
	private static final String EXCEPTION_STRING = "You have selected an illegal number of assistant";
	
	@Override
	public String toString() {
		return EXCEPTION_STRING;
	}
}
