package it.polimi.ingsw.ps23.client.socket.gui;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.Parser;
import it.polimi.ingsw.ps23.client.socket.SocketClient;

public class KingPositionExpression implements Parser {

	private Expression expression;
	
	public KingPositionExpression(SwingUI swingUI, Expression expression) {
		this.expression = expression;
	}

	@Override
	public String parse(String message) {
		if(expression.interpret(message)) {
			String updatedMessage = message;
			updatedMessage = expression.removeTag(updatedMessage);
			
			return updatedMessage;
		}
		return message;
	}

}
