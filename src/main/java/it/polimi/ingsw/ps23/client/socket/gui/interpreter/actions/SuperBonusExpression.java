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
			swingUI.appendConsoleText("\n\n" + guiView.getClient().receive());
			swingUI.enableRegionButtons(true);
			guiView.pause();
			guiView.getClient().send(swingUI.getChosenRegion());
		}
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			Expression dynamicContent = new TerminalExpression(REFRESH_CONTENT_TAG_OPEN, REFRESH_CONTENT_TAG_CLOSE);
			updateDynamicContent(swingUI, dynamicContent.selectBlock(message));
			String selectedItem;
			boolean otherBonus = Boolean.valueOf(guiView.getClient().receive());
			while(otherBonus) {
				int numberOfCurrentBonus = Integer.parseInt(guiView.getClient().receive());
				for (int numberOfBonuses = 0; numberOfBonuses < numberOfCurrentBonus; numberOfBonuses++) {
					additionalOutput(Boolean.valueOf(guiView.getClient().receive()));
					swingUI.appendConsoleText("\n\n" + guiView.getClient().receive());
					
					otherBonus = Boolean.valueOf(guiView.getClient().receive());
				}
			}
		}
	}

}
