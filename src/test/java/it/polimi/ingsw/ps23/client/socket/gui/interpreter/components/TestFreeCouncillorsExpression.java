package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
/**
 * Tests if the correct info are obtained from a message after {@link FreeCouncillorsExpression} parsing.
 * @author Mirco Manzoni
 *
 */
public class TestFreeCouncillorsExpression {

	@Test
	public void test() {
		List<String> players = new ArrayList<>();
		players.add("Player 1");
		Game game = new Game(players);
		FreeCouncillorsExpression freeCouncillorsExpression = new FreeCouncillorsExpression(new TerminalExpression("<free_councillors>", "</free_councillors>"));
		String message = addFreeCouncillors(game.getFreeCouncillors().getFreeCouncillorsList());
		freeCouncillorsExpression.parse(message);
		for(Councillor councillor : game.getFreeCouncillors().getFreeCouncillorsList()) {
			assertTrue(freeCouncillorsExpression.getFreeCouncillors().contains(councillor.toString()));
		}		
	}
	
	private String addFreeCouncillors(List<Councillor> freeCouncillors) {
		StringBuilder freeCouncillorsSend = new StringBuilder();
		for(Councillor councillor : freeCouncillors) {
			freeCouncillorsSend.append(councillor.getColor() + ",");
		}
		return "<free_councillors>" + freeCouncillorsSend + "</free_councillors>";
	}

}
