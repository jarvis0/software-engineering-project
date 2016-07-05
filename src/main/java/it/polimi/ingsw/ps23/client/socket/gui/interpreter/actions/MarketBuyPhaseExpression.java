package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

class MarketBuyPhaseExpression extends GUIParser {
	
	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;

	private Expression expression;
	
	MarketBuyPhaseExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			String player = guiView.getClient().receive();
			swingUI.setConsoleText("\nIt's " + player + " market phase turn.");
			if(player.equals(guiView.getPlayerName())) {
				boolean canBuy = Boolean.valueOf(guiView.getClient().receive());
				if (canBuy) {
					swingUI.setConsoleText("\nChoose the offert that you want to buy: \n" + guiView.getClient().receive());
					swingUI.enableMarketInputArea(true);
					guiView.pause();
					swingUI.enableMarketInputArea(false);
					guiView.getClient().send(String.valueOf(swingUI.getChosenValue() - 1));
				} else {
					swingUI.appendConsoleText("You can buy nothing.");
				}
			}
		}
	}

}
