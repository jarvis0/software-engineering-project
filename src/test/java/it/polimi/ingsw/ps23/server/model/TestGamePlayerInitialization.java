package it.polimi.ingsw.ps23.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
/**
 * Tests if the all the players who want to join the game starting really that game.
 * @author Mirco Manzoni
 *
 */
public class TestGamePlayerInitialization {

	@Test
	public void test() {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		playersName.add("b");
		Game game = new Game(playersName);
		assertTrue(game.getGamePlayersSet().getPlayers().get(0).getName().equals("a") && game.getGamePlayersSet().getPlayers().get(1).getName().equals("b"));
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		assertTrue(game.getCurrentPlayer().equals(game.getGamePlayersSet().getPlayers().get(0)));
	}

}
