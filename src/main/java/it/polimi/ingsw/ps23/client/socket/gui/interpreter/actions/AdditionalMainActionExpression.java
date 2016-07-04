package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

class AdditionalMainActionExpression extends GUIParser {

	private SocketSwingUI swingUI;
	
	private Expression expression;
	
	AdditionalMainActionExpression(SocketSwingUI swingUI, Expression expression) {
		this.swingUI = swingUI;
		this.expression = expression;
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			swingUI.appendConsoleText("\n\nYou are performing an Additional Main Action quick action");
		}
	}

}
