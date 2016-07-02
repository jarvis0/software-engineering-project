package it.polimi.ingsw.ps23.client.socket.gui;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;

public class AcquireBusinessPermitTileExpression extends GUIComponentsParser {
		
	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;
	
	private Expression expression;
	
	public AcquireBusinessPermitTileExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}

	@Override//change to protected
	public void parse(String message) {
		if(expression.interpret(message)) {
			swingUI.clearSwingUI();
			swingUI.showAvailableActions(false, false);
			swingUI.enableButtons(true);
			guiView.pause();
			swingUI.enableButtons(false);
			String chosenCouncil = swingUI.getChosenRegion();
			guiView.getClient().send(chosenCouncil);
			swingUI.enablePoliticCards(true);
			int numberOfCards = 4;
			boolean finish = false;
			int i = 0;
			List<String> chosenCards = new ArrayList<>();
			while (i < numberOfCards /*&& i < currentState.getPoliticHandSize()*/ && !finish) {
				guiView.pause();
				finish = swingUI.hasFinished();
				if(!finish) {
					chosenCards.add(swingUI.getChosenCard());
				}
				i++;
			}
			for(i = 0; i < chosenCards.size() - 1; i++) {
				guiView.getClient().send(chosenCards.get(i));
			}
			guiView.getClient().send(chosenCards.get(chosenCards.size() - 1) + "<end_loop>");
			swingUI.enablePoliticCards(false);
			swingUI.enablePermitTilesPanel(chosenCouncil);
			guiView.pause();
			guiView.getClient().send(String.valueOf(swingUI.getChosenTile()));
		}
	}

}
