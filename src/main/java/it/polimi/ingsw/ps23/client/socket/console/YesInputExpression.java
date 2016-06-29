package it.polimi.ingsw.ps23.client.socket.console;

import java.io.PrintStream;
import java.util.Scanner;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.Parser;
import it.polimi.ingsw.ps23.client.socket.SocketClient;

public class YesInputExpression implements Parser {

	private Scanner scanner;
	private PrintStream output;
	private SocketClient socketClient;
	
	private Expression expression;
	
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
			updatedMessage = expression.removeTag(updatedMessage);
			output.println(updatedMessage);
			socketClient.send(scanner.nextLine());
			return updatedMessage;
		}
		return message;
	}

}
