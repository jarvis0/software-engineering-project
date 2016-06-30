package it.polimi.ingsw.ps23.client.socket.gui;

import java.io.PrintStream;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.Parser;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;

public class NoInputExpression implements Parser {

	private static final String MAP_TYPE_TAG_OPEN = "<map_type>";
	private static final String MAP_TYPE_TAG_CLOSE = "</map_type>";
	
	private PrintStream output;
	
	private Expression expression;
	
	private MapTypeExpression isMapType;
	
	public NoInputExpression(RemoteGUIView remoteView, PrintStream output, Expression expression) {
		this.output = output;
		this.expression = expression;
		isMapType = getMapTypeExpression(remoteView);
	}
	
	private MapTypeExpression getMapTypeExpression(RemoteGUIView remoteView) {
		Expression mapTypeExpression = new TerminalExpression(MAP_TYPE_TAG_OPEN, MAP_TYPE_TAG_CLOSE);
		return new MapTypeExpression(remoteView, output, mapTypeExpression);
	}
	
	@Override
	public String parse(String message) {
		String updatedMessage;
		if(expression.interpret(message)) {
			updatedMessage = expression.selectBlock(message);
			String mapType = isMapType.parse(updatedMessage);
			if(mapType.equals(updatedMessage)) {
				output.println(updatedMessage);
			}
			return mapType;
		}
		return message;
	}

}
