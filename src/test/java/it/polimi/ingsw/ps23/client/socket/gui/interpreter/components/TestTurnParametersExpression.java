package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.player.Player;
/**
 * Tests if the correct info are obtained from a message after {@link TurnParametersExpression} parsing.
 * @author Mirco Manzoni
 *
 */
public class TestTurnParametersExpression {

	@Test
	public void test() {
		List<String> players = new ArrayList<>();
		players.add("Player 1");
		Game game = new Game(players);
		String message = addTurnParameters(game.getGamePlayersSet().getPlayer(0), true, true);
		TurnParametersExpression turnParametersExpression = new TurnParametersExpression(new TerminalExpression("<turn_parameters>", "</turn_parameters>"));
		turnParametersExpression.parse(message);
		assertTrue(turnParametersExpression.getCurrentPlayer().contains(players.get(0)));
		assertTrue(turnParametersExpression.isAvailableMainAction());
		assertTrue(turnParametersExpression.isAvailableQuickAction());
	}
	
	private String addTurnParameters(Player currentPlayer, boolean availableMainAction, boolean availableQuickAction) {
		StringBuilder turnParametersSend = new StringBuilder();
		turnParametersSend.append(currentPlayer.getName());
		turnParametersSend.append("," + availableMainAction);
		turnParametersSend.append("," + availableQuickAction);
		turnParametersSend.append(",");
		return "<turn_parameters>" + turnParametersSend + "</turn_parameters>";
	}

}
