package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

public class MarketOfferPhaseExpression extends GUIParser {

	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;
	
	private Expression expression;
	
	MarketOfferPhaseExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}
	
	private void sellPoliticCard(boolean canSellPoliticCards) {
		List<String> chosenPoliticCards = new ArrayList<>();
		if (canSellPoliticCards) {
			swingUI.setConsoleText("\nHow many politic cards do you want to sell? ");
			swingUI.enableMarketInputArea(true);
			guiView.pause();
			int numberOfCards = swingUI.getChosenValue();
			swingUI.enableMarketInputArea(false);
			swingUI.enablePoliticCards(true);
			swingUI.setConsoleText("\nPlease, press on the cards that you want to sell.");
			int politicHandNumber = Integer.parseInt(guiView.getClient().receive());
			for (int i = 0; i < numberOfCards && i < politicHandNumber; i++) {
				guiView.pause();
				chosenPoliticCards.add(swingUI.getChosenCard());
			}
			guiView.getClient().send(String.valueOf(chosenPoliticCards.size()));
			for(String card : chosenPoliticCards) {
				guiView.getClient().send(card);
			}
		}
	}

	private void sellPermissionCard(boolean canSellPermitTiles) {
		List<Integer> chosenPermissionCards = new ArrayList<>();
		if (canSellPermitTiles) {
			swingUI.setConsoleText("\nHow many permission cards do you want to use? (numerical input > 0)");
			swingUI.enableMarketInputArea(true);
			guiView.pause();
			int numberOfCards = swingUI.getChosenValue();
			swingUI.enableMarketInputArea(false);
			swingUI.enablePermitTileDeck(true);
			swingUI.setConsoleText("\nPlease, press on the cards that you want to sell.");
			int permitHandNumber = Integer.parseInt(guiView.getClient().receive());
			for (int i = 0; i < numberOfCards && i < permitHandNumber; i++) {
				guiView.pause();
				chosenPermissionCards.add(swingUI.getChosenTile());
			}
			guiView.getClient().send(String.valueOf(chosenPermissionCards.size()));
			for(Integer card : chosenPermissionCards) {
				guiView.getClient().send(String.valueOf(card));
			}
		}
	}
	
	private void sellAssistant(boolean canSellAssistants) {
		if (canSellAssistants) {
			swingUI.setConsoleText("\nSelect the number of assistants " + guiView.getClient().receive() + ".");
			swingUI.enableMarketInputArea(true);
			guiView.pause(); 
			guiView.getClient().send(String.valueOf(swingUI.getChosenValue()));
		}
	}
	
	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			String player = guiView.getClient().receive();
			swingUI.setConsoleText("\nIt's " + player + " market phase turn.");
			if (player.equals(guiView.getPlayerName())) {
				sellPoliticCard(Boolean.valueOf(guiView.getClient().receive()));
				sellPermissionCard(Boolean.valueOf(guiView.getClient().receive()));
				sellAssistant(Boolean.valueOf(guiView.getClient().receive()));
				swingUI.setConsoleText("\nChoose the price for your offer: ");
				swingUI.enableMarketInputArea(true);
				guiView.pause();
				guiView.getClient().send(String.valueOf(swingUI.getChosenValue()));
				swingUI.enableMarketInputArea(false);
			} else {
				swingUI.showAvailableActions(false, false);
			}
		}
	}

}
