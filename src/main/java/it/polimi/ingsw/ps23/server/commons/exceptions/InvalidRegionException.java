package it.polimi.ingsw.ps23.server.commons.exceptions;

import java.io.IOException;

public class InvalidRegionException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8524929440120895842L;
	private static final String EXCEPTION_STRING = "You have selected an illegal region.";
	
	@Override
	public String toString() {
		return EXCEPTION_STRING;
	}
}
