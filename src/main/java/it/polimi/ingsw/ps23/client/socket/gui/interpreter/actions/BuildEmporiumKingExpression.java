package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

class BuildEmporiumKingExpression extends GUIParser {

	private static final int MAX_CARDS_NUMBER = 4;
	
	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;
	
	private Expression expression;
	
	BuildEmporiumKingExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			List<String> removedCards = new ArrayList<>();
			swingUI.clearSwingUI();
			swingUI.showAvailableActions(false, false);
			swingUI.enablePoliticCards(true);
			swingUI.enableFinish(false);
			swingUI.appendConsoleText("\n\nYou are performing a Build Emporium King main action,\npress on the politic cards you want to use to satisfy the King's council.");
			boolean finish = false;
			int politicHandSize = Integer.parseInt(guiView.getClient().receive());
			int i = 0;
			while (i < MAX_CARDS_NUMBER && i < politicHandSize && !finish) {
				guiView.pause();
				finish = swingUI.hasFinished();
				swingUI.enableFinish(true);
				if(!finish) {
					removedCards.add(swingUI.getChosenCard());
				}
				i++;
			}
			swingUI.appendConsoleText("\nYou have selected these politic cards:\n" + removedCards + "\nplease press on the city where you want to move the King.");
			swingUI.enablePoliticCards(false);
			swingUI.enableCities(true);
			guiView.pause();
			swingUI.enableCities(false);
			String arrivalCity = swingUI.getChosenCity();
			guiView.getClient().send(String.valueOf(removedCards.size()));
			for(String card : removedCards) {
				guiView.getClient().send(card);
			}
			guiView.getClient().send(arrivalCity);
		}
	}

}
