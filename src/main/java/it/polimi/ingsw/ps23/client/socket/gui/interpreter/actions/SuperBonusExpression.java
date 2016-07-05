package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.RefreshContent;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

class SuperBonusExpression extends RefreshContent {

	private static final String REFRESH_CONTENT_TAG_OPEN = "<refresh_content>";
	private static final String REFRESH_CONTENT_TAG_CLOSE = "</refresh_content>";
	
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
			swingUI.appendConsoleText("\n\nYou have encountred a Building Permit Bonus on Nobility Track.\nPress the region where to pick a permit tile.");
			swingUI.enableRegionButtons(true);
			guiView.pause();
			swingUI.enableRegionButtons(false);
			guiView.getClient().send(swingUI.getChosenRegion());
		}
	}

	private void performSuperBonus() {
		String selectedItem = new String();
		swingUI.appendConsoleText("\n\n" + guiView.getClient().receive());
		if(Boolean.valueOf(guiView.getClient().receive())) {
			swingUI.enableTotalHandDeck(true);
			guiView.pause();
			swingUI.enableTotalHandDeck(false);
			selectedItem = String.valueOf(swingUI.getChosenTile() + 1);
		}
		if(Boolean.valueOf(guiView.getClient().receive())) {
			swingUI.enableCities(true);
			guiView.pause();//TODO
			swingUI.enableCities(false);
			selectedItem = swingUI.getChosenCity();
		}
		if(Boolean.valueOf(guiView.getClient().receive())) {
			swingUI.enablePermitTilesPanel(swingUI.getChosenRegion(), true);
			guiView.pause();
			swingUI.enablePermitTilesPanel(swingUI.getChosenRegion(), false);
			selectedItem = String.valueOf(swingUI.getChosenTile());
		}
		guiView.getClient().send(selectedItem);
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			Expression dynamicContent = new TerminalExpression(REFRESH_CONTENT_TAG_OPEN, REFRESH_CONTENT_TAG_CLOSE);
			updateDynamicContent(swingUI, dynamicContent.selectBlock(message));
			Boolean otherBonus = Boolean.valueOf(guiView.getClient().receive());
			while(otherBonus) {
				int numberOfCurrentBonus = Integer.parseInt(guiView.getClient().receive());
				for (int numberOfBonuses = 0; numberOfBonuses < numberOfCurrentBonus; numberOfBonuses++) {
					additionalOutput(Boolean.valueOf(guiView.getClient().receive()));
					performSuperBonus();
				}
				otherBonus = Boolean.valueOf(guiView.getClient().receive());
			}
		}
	}

}
