package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;

class KingPositionExpression extends GUIParser {

	private Expression expression;
	
	private String kingPosition;
	
	KingPositionExpression(Expression expression) {
		this.expression = expression;
	}

	String getKingPosition() {
		return kingPosition;
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			kingPosition = expression.selectBlock(message);
		}
	}

}
