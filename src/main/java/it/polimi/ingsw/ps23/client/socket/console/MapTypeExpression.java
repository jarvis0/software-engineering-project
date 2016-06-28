package it.polimi.ingsw.ps23.client.socket.console;

import java.io.PrintStream;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.Parser;

class MapTypeExpression implements Parser {

	PrintStream output;
	
	private Expression expression;
	
	MapTypeExpression(PrintStream output, Expression expression) {
		this.output = output;
		this.expression = expression;
	}
	
	@Override
	public String parse(String message) {
		if(expression.interpret(message)) {
			output.print("\nMap type: " + expression.removeTag(message) + ".");
			return expression.removeBlock(message);
		}
		return message;
	}

}
