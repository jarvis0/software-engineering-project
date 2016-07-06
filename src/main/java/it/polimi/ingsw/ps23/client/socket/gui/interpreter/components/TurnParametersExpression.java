package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;

class TurnParametersExpression extends GUIParser {

	private Expression expression;
	
	private String currentPlayer;
	private boolean isAvailableMainAction;
	private boolean isAvailableQuickAction;
	
	TurnParametersExpression(Expression expression) {
		this.expression = expression;
	}
	
	String getCurrentPlayer() {
		return currentPlayer;
	}
	
	boolean isAvailableMainAction() {
		return isAvailableMainAction;
	}
	
	boolean isAvailableQuickAction() {
		return isAvailableQuickAction;
	}
	
	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			String parsingMessage = expression.selectBlock(message);
			String field = parsingMessage.substring(0, parsingMessage.indexOf(','));
			currentPlayer = field;
			parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
			field = parsingMessage.substring(0, parsingMessage.indexOf(','));
			isAvailableMainAction = Boolean.parseBoolean(field);
			parsingMessage = parsingMessage.substring(parsingMessage.indexOf(',') + 1);
			field = parsingMessage.substring(0, parsingMessage.indexOf(','));
			isAvailableQuickAction = Boolean.parseBoolean(field);
		}
	}

}
