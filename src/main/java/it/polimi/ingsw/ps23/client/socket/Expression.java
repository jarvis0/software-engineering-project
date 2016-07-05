package it.polimi.ingsw.ps23.client.socket;
/**
 * Provides methods to manage strings.
 * @author Giuseppe Mascellaro
 *
 */
public interface Expression {
	/**
	 * Calculates if the message is of the type of the object.
	 * @param message - the message to analyze
	 * @return true if can interpret, false otherwise
	 */
	public boolean interpret(String message);
	/**
	 * Extracts a string from 2 defined tags.
	 * @param message - the string to manipulate
	 * @return the string extracted
	 */
	public String selectBlock(String message);
	/**
	 * Removes from a string the substring within 2 defined tags.
	 * @param message - the string to manipulate
	 * @return the string without the substring
	 */
	public String removeBlock(String message);
	/**
	 * Removes tags from the string.
	 * @param message - the string to manipulate
	 * @return the string with removed tags
	 */
	public String removeTags(String message);
	
}
