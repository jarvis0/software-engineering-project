package it.polimi.ingsw.ps23.server.commons.exceptions;

import java.io.IOException;
/**
 * IOexception that notify the players that the current {@link Player} have selected an illegal cost for {@link MarketObject}.
 * @author Mirco Manzoni
 *
 */
public class InvalidCostException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8149078451975580160L;
	private static final String EXCEPTION_STRING = "The current player has selected an illegal cost for your offer.";
	
	@Override
	public String toString() {
		return EXCEPTION_STRING;
	}
}
