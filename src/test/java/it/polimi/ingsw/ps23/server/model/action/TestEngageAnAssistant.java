package it.polimi.ingsw.ps23.server.model.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.actions.EngageAnAssistant;

public class TestEngageAnAssistant {

	@Test
	public void test() throws InsufficientResourcesException {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		int initialCoin = game.getCurrentPlayer().getCoins();
		int initialAssistant = game.getCurrentPlayer().getAssistants();
		TurnHandler turnHandler = new TurnHandler();
		EngageAnAssistant action = new EngageAnAssistant();
		action.doAction(game, turnHandler);
		assertTrue(game.getCurrentPlayer().getAssistants() == initialAssistant + 1);
		assertTrue(game.getCurrentPlayer().getCoins() == initialCoin - 3);
		
		
	}

}
