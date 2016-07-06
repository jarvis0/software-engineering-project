package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
import it.polimi.ingsw.ps23.server.model.Game;
/**
  * Tests if the correct info are obtained from a message after {@link KingPositionExpression} parsing.
 * @author Mirco Manzoni
 *
 */
public class TestKingPositionExpression {

	@Test
	public void test() {
		List<String> players = new ArrayList<>();
		players.add("Player 1");
		Game game = new Game(players);
		String message = addKingPosition(game.getKing().getPosition().getName());
		KingPositionExpression kingPositionExpression = new KingPositionExpression(new TerminalExpression("<king_position>", "</king_position>"));
		kingPositionExpression.parse(message);
		assertTrue(kingPositionExpression.getKingPosition().contains(game.getKing().getPosition().getName()));
	}

	private String addKingPosition(String kingPosition) {
		return "<king_position>" + kingPosition + "</king_position>";
	}
}
