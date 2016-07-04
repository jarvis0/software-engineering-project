package it.polimi.ingsw.ps23.client.socket.gui.interpreter.actions;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.components.SocketSwingUI;

class AssistantToElectCouncillorExpression extends GUIParser {

	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;
	
	private Expression expression;
	
	AssistantToElectCouncillorExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}

	@Override
	protected void parse(String message) {
		if(expression.interpret(message)) {
			swingUI.clearSwingUI();
			swingUI.appendConsoleText("\n\nYou are performing an Assistant To Elect Councillor quick action,\npress on a free councillor to select it.");
			swingUI.showAvailableActions(false, false);
			swingUI.enableFreeCouncillorsButtons(true);
			guiView.pause();
			String chosenCouncillor = swingUI.getChosenCouncillor();
			swingUI.appendConsoleText("\nYou have chosen a " + chosenCouncillor + " Councillor,\npress on the region where you want to put it.");
			swingUI.enableFreeCouncillorsButtons(false);
			swingUI.enableRegionButtons(true);
			swingUI.enableKingButton(true);
			guiView.pause();
			String chosenBalcony = swingUI.getChosenRegion();
			swingUI.appendConsoleText("\nYou have just elected a " + chosenCouncillor + "councillor in " + chosenBalcony + "'s balcony");
			guiView.getClient().send(chosenCouncillor);
			guiView.getClient().send(chosenBalcony);
		}
	}

}
