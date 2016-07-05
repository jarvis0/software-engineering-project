package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;

public class DynamicContentsExpression extends RefreshContent {
	
	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;
	
	private Expression expression;
	
	public DynamicContentsExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}
	
	@Override
	public void parse(String message) {
		if(expression.interpret(message)) {
			String noTagMessage = expression.selectBlock(message);
			getDynamicContent(swingUI, noTagMessage);
			String currentPlayer = guiView.getClient().receive();
			if(currentPlayer.equals(guiView.getPlayerName())) {
				boolean isAvailableMainAction = Boolean.valueOf(guiView.getClient().receive());
				boolean isAvailableQuickAction = Boolean.valueOf(guiView.getClient().receive());
				swingUI.appendConsoleText("\nIt's your turn, please select an action from the pool displayed above.");
				swingUI.showAvailableActions(isAvailableMainAction, isAvailableQuickAction);
				guiView.pause();
				guiView.getClient().send(swingUI.getChosenAction());
			}
			else {
				swingUI.setConsoleText("\nIt's " + currentPlayer + "'s turn.");
				swingUI.showAvailableActions(false, false);
			}
		}
	}

}
