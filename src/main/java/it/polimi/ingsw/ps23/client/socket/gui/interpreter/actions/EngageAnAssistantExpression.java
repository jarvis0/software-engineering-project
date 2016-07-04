package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

class EngageAnAssistantExpression extends GUIParser {

	private SocketSwingUI swingUI;
	
	private Expression expression;
	
	EngageAnAssistantExpression(SocketSwingUI swingUI, Expression expression) {
		this.swingUI = swingUI;
		this.expression = expression;
	}

	
	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			swingUI.appendConsoleText("\n\nYou are performing a Engage An Assistant quick action");
		}
	}

}
