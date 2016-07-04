package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

class ChangePermitTilesExpression extends GUIParser {
	
	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;

	private Expression expression;
	
	ChangePermitTilesExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			swingUI.appendConsoleText("\n\nYou are performing a Change Permits Tile quick action,\n please select the region where you what to change tiles.");
			swingUI.showAvailableActions(false, false);
			swingUI.enableRegionButtons(true);
			guiView.pause();
			String chosenRegion = swingUI.getChosenRegion();
			swingUI.enableRegionButtons(false);
			swingUI.appendConsoleText("\nYou have just changed the " + chosenRegion + "'s Permit Tiles");
			guiView.getClient().send(chosenRegion);
		}
	}

}
