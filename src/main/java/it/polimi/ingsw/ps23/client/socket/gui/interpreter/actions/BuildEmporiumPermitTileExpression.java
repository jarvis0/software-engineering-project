package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

class BuildEmporiumPermitTileExpression extends GUIParser {

	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;
	
	private Expression expression;
	
	BuildEmporiumPermitTileExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			swingUI.clearSwingUI();
			swingUI.showAvailableActions(false, false);
			swingUI.enablePermitTileDeck(true);
			swingUI.appendConsoleText("\n\nYou are performing a Build Emporium Permit Tile Main Action,\npress on the permit tile that you want use.");
			guiView.pause();
			int chosenCard = swingUI.getChosenTile();
			swingUI.appendConsoleText("\nYou have chose tile number: " + chosenCard + "\npress on the city want to build.");
			swingUI.enableCities(true);
			guiView.pause();
			swingUI.enableCities(false);
			String chosenCity = swingUI.getChosenCity();
			guiView.getClient().send(chosenCity);
			guiView.getClient().send(String.valueOf(chosenCard));
		}
	}

}
