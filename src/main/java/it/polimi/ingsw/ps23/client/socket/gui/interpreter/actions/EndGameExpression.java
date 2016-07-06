package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.RefreshContent;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

class EndGameExpression extends RefreshContent {

	private static final String REFRESH_CONTENT_TAG_OPEN = "<refresh_content>";
	private static final String REFRESH_CONTENT_TAG_CLOSE = "</refresh_content>";
	
	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;

	private Expression expression;
	
	EndGameExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			Expression dynamicContent = new TerminalExpression(REFRESH_CONTENT_TAG_OPEN, REFRESH_CONTENT_TAG_CLOSE);
			updateDynamicContent(swingUI, dynamicContent.selectBlock(message));
			swingUI.appendConsoleText("\n\n" + guiView.getClient().receive());
			guiView.setEndGame();
		}
	}

}
