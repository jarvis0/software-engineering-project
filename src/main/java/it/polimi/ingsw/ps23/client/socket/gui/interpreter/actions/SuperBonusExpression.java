package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

class SuperBonusExpression extends GUIParser {

	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;

	private Expression expression;
	
	SuperBonusExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}
	
	
	private void additionalOutput(boolean isBuildingPermitTileBonus) {
		if (isBuildingPermitTileBonus) {
			swingUI.setConsoleText("\n\n" + guiView.getClient().receive());
			swingUI.enableRegionButtons(true);
			guiView.pause();
			guiView.getClient().send(swingUI.getChosenRegion());
		}
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			String selectedItem;
			boolean otherBonus = Boolean.valueOf(guiView.getClient().receive());
			while(otherBonus) {
				int numberOfCurrentBonus = Integer.parseInt(guiView.getClient().receive());
				for (int numberOfBonuses = 0; numberOfBonuses < numberOfCurrentBonus; numberOfBonuses++) {
					additionalOutput(Boolean.valueOf(guiView.getClient().receive()));
					swingUI.setConsoleText("\n\n" + guiView.getClient().receive());
					if(Boolean.valueOf(guiView.getClient().receive())) {
						//swingUI.enableTotalHandDeck(true);TODO
						guiView.pause();
						selectedItem = String.valueOf(swingUI.getChosenTile());
					}
					if(Boolean.valueOf(guiView.getClient().receive())) {
						swingUI.enableCities(true);
						guiView.pause();
						selectedItem = swingUI.getChosenCity();
					}
					else {
						swingUI.enablePermitTilesPanel(swingUI.getChosenRegion());
						guiView.pause();
						selectedItem = String.valueOf(swingUI.getChosenTile());
					}
					guiView.getClient().send(selectedItem);
				}
				otherBonus = Boolean.valueOf(guiView.getClient().receive());
			}
		}
	}

}
