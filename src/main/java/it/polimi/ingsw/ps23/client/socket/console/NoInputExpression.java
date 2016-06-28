package it.polimi.ingsw.ps23.client.socket.console;

import java.io.PrintStream;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.Parser;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;

public class NoInputExpression implements Parser {

	private static final String MAP_TYPE_TAG_OPEN = "<map_type>";
	private static final String MAP_TYPE_TAG_CLOSE = "</map_type>";
	
	private PrintStream output;
	
	private Expression expression;
	
	public NoInputExpression(PrintStream output, Expression expression) {
		this.output = output;
		this.expression = expression;
	}
	
	private MapTypeExpression getMapTypeExpression() {
		Expression mapTypeExpression = new TerminalExpression(MAP_TYPE_TAG_OPEN, MAP_TYPE_TAG_CLOSE);
		return new MapTypeExpression(output, mapTypeExpression);
	}
	
	@Override
	public String parse(String message) {
		MapTypeExpression isMapType = getMapTypeExpression();
		String updatedMessage;
		if(expression.interpret(message)) {
			updatedMessage = expression.removeTag(message);
			updatedMessage = isMapType.parse(updatedMessage);
			//TODO others tags controls
			output.println(updatedMessage);
		}
		return message;
	}

}
