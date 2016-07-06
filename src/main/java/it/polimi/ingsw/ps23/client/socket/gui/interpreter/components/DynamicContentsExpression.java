package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.RemoteGUIView;
/**
 * Provides methods to parse and get available all the dynamics info of the game like 
 * {@link King}, {@link FreeCouncillors}, {@link Council}, {@link Player}, {@link GroupRegionalCities}
 * {@link TurnHandler} status.
 * 
 * @author Giuseppe Mascellaro
 *
 */
public class DynamicContentsExpression extends RefreshContent {
	
	private SocketSwingUI swingUI;
	
	private RemoteGUIView guiView;
	
	private Expression expression;
	/**
	 * Constructs the object initializing all the variables to the default values.
	 * @param swingUI - Interface to interact with UI
	 * @param guiView - The current socket view
	 * @param expression - Tags used in socket protocol
	 */
	public DynamicContentsExpression(SocketSwingUI swingUI, RemoteGUIView guiView, Expression expression) {
		this.swingUI = swingUI;
		this.guiView = guiView;
		this.expression = expression;
	}
	
	@Override
	public void parse(String message) {
		if(expression.interpret(message)) {
			String noTagMessage = expression.selectBlock(message);
			updateDynamicContent(swingUI, noTagMessage);
			swingUI.appendConsoleText(guiView.getClient().receive());
			String currentPlayer = guiView.getClient().receive();
			if(currentPlayer.equals(guiView.getPlayerName())) {
				Boolean isAvailableMainAction = Boolean.valueOf(guiView.getClient().receive());
				Boolean isAvailableQuickAction = Boolean.valueOf(guiView.getClient().receive());
				swingUI.appendConsoleText("\nIt's your turn, please select an action from the pool displayed above.");
				swingUI.showAvailableActions(isAvailableMainAction, isAvailableQuickAction);
				guiView.pause();
				guiView.getClient().send(swingUI.getChosenAction());
			}
			else {
				swingUI.appendConsoleText("\nIt's " + currentPlayer + "'s turn.\n");
				swingUI.showAvailableActions(false, false);
			}
		}
	}

}
