package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

class AcquireBusinessPermitTileExpression extends GUIParser {
		
	private static final int MAX_CARDS_NUMBER = 4;
	
	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;
	
	private Expression expression;
	
	AcquireBusinessPermitTileExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			swingUI.clearSwingUI();
			swingUI.showAvailableActions(false, false);
			swingUI.enableRegionButtons(true);
			guiView.pause();
			swingUI.enableRegionButtons(false);
			String chosenCouncil = swingUI.getChosenRegion();
			swingUI.enablePoliticCards(true);
			boolean finish = false;
			int politicHandSize = Integer.parseInt(guiView.getClient().receive());
			int i = 0;
			List<String> chosenCards = new ArrayList<>();
			while (i < MAX_CARDS_NUMBER && i < politicHandSize && !finish) {
				guiView.pause();
				finish = swingUI.hasFinished();
				swingUI.enableFinish(true);
				if(!finish) {
					chosenCards.add(swingUI.getChosenCard());
				}
				i++;
			}
			swingUI.enablePoliticCards(false);
			swingUI.enablePermitTilesPanel(chosenCouncil);
			guiView.pause();
			guiView.getClient().send(chosenCouncil);
			guiView.getClient().send(String.valueOf(chosenCards.size()));
			for(String card : chosenCards) {
				guiView.getClient().send(card);
			}
			guiView.getClient().send(String.valueOf(swingUI.getChosenTile()));
		}
	}

}
