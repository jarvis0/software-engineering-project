package it.polimi.ingsw.ps23.client.socket.gui;

import java.io.PrintStream;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.Parser;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;

class MapTypeExpression implements Parser {

	private RemoteGUIView remoteView;
	
	private PrintStream output;
	
	private Expression expression;
	
	MapTypeExpression(RemoteGUIView remoteView, PrintStream output, Expression expression) {
		this.remoteView = remoteView;
		this.output = output;
		this.expression = expression;
	}
	
	@Override
	public String parse(String message) {
		if(expression.interpret(message)) {
			String mapType = expression.selectBlock(message);
			output.print("\nMap type: " + mapType + ".");
			remoteView.setEndCLIPrints();
			return mapType;
		}
		return message;
	}

}
