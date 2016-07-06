package it.polimi.ingsw.ps23.client.socket.console;

import java.io.PrintStream;
import java.util.Scanner;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.Parser;
import it.polimi.ingsw.ps23.client.socket.SocketClient;

/**
 * This class provides a yes input expression pattern
 * @author Giuseppe Mascellaro
 *
 */
public class YesInputExpression implements Parser {

	private Scanner scanner;
	private PrintStream output;
	private SocketClient socketClient;
	
	private Expression expression;
	
	/**
	 * Saves all needed for use this expression for interpret a regular expression string.
	 * @param scanner - scanner for read a string via console
	 * @param output - scanner for acquire a console input
	 * @param socketClient - socket client used for communicate with the remote server
	 * @param expression - regula expression to be parsed
	 */
	public YesInputExpression(Scanner scanner, PrintStream output, SocketClient socketClient, Expression expression) {
		this.scanner = scanner;
		this.output = output;
		this.socketClient = socketClient;
		this.expression = expression;
	}
	
	@Override
	public String parse(String message) {
		if(expression.interpret(message)) {
			String updatedMessage = message;
			updatedMessage = expression.selectBlock(updatedMessage);
			output.println(updatedMessage);
			socketClient.send(scanner.nextLine());
			return updatedMessage;
		}
		return message;
	}

}
