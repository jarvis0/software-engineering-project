package it.polimi.ingsw.ps23.server.model.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidRegionException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.actions.ChangePermitsTile;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;

public class TestChangePermitTile {

	@Test
	public void test() throws InsufficientResourcesException, InvalidRegionException {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		int initialAssistant = game.getCurrentPlayer().getAssistants();
		TurnHandler turnHandler = new TurnHandler();
		List<Card> deck = new ArrayList<>();
		deck.addAll(((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(0))).getPermitTilesUp().getCards());
		ChangePermitsTile action = new ChangePermitsTile(game.getGameMap().getGroupRegionalCity().get(0).getName());
		action.doAction(game, turnHandler);
		for(Card card : deck) {
			assertFalse(((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(0))).getPermitTilesUp().getCards().contains(card));
		}
		assertTrue(initialAssistant -1 == game.getCurrentPlayer().getAssistants());
		assertFalse(turnHandler.isAvailableQuickAction());
		
	}

}
