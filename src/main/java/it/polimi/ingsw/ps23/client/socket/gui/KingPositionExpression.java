package it.polimi.ingsw.ps23.client.socket.gui;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.Parser;
import it.polimi.ingsw.ps23.client.socket.SocketSwingUI;

public class KingPositionExpression implements Parser {

	private SocketSwingUI swingUI;
	
	private Expression expression;
	
	public KingPositionExpression(SocketSwingUI swingUI, Expression expression) {
		this.expression = expression;
		this.swingUI = swingUI;
	}

	@Override
	public String parse(String message) {
		if(expression.interpret(message)) {
			swingUI.refreshKingPosition(expression.removeTag(message));
			return expression.removeBlock(message);
		}
		return message;
	}

}
