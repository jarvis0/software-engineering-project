package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

class ChangePermitTilesExpression extends GUIParser {
	
	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;

	private Expression expression;
	
	ChangePermitTilesExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			swingUI.enableButtons(true);
			guiView.pause();
			String chosenRegion = swingUI.getChosenRegion();
			swingUI.enableButtons(false);
			guiView.getClient().send(chosenRegion);
		}
	}

}
