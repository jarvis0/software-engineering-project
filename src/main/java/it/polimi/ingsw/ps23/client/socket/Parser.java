package it.polimi.ingsw.ps23.client.socket;

/**
 * Provides a functional interface for parsing string sent via socket.
 * @author Giuseppe Mascellaro
 *
 */
@FunctionalInterface
public interface Parser {
	
	/**
	 * Method for parsing all kind of communication protocol strings for socket.
	 * @param message - to be parsed and used in various ways
	 * @return
	 */
	public String parse(String message);
	
}
