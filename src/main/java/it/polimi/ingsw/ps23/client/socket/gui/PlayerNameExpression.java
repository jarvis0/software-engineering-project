package it.polimi.ingsw.ps23.client.socket.gui;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.Parser;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;

class PlayerNameExpression implements Parser {

	private RemoteGUIView remoteView;
	
	private Expression expression;
	
	PlayerNameExpression(RemoteGUIView remoteView, Expression expression) {
		this.remoteView = remoteView;
		this.expression = expression;
	}
	
	@Override
	public String parse(String message) {
		if(expression.interpret(message)) {
			String playerName = expression.selectBlock(message);
			remoteView.setPlayerName(playerName);
			return expression.removeTags(message);
		}
		return message;
	}

}
