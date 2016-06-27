package it.polimi.ingsw.ps23.server.model.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncilException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncillorException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.actions.ElectCouncillor;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;

public class TestElectCouncillor {

	@Test
	public void test() throws InvalidCouncillorException, InvalidCouncilException {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		int initialCoin = game.getCurrentPlayer().getCoins();
		TurnHandler turnHandler = new TurnHandler();
		String councillor = game.getFreeCouncillors().getFreeCouncillors().get(0).getColor().toString();
		Council council = ((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(0))).getCouncil();
		String councilName = game.getGameMap().getGroupRegionalCity().get(0).getName();
		ElectCouncillor action = new ElectCouncillor(councillor, councilName);
		action.doAction(game, turnHandler);
		assertTrue(!turnHandler.isAvailableMainAction());
		Iterator<Councillor> iterator = council.getCouncil().iterator();
		iterator.next();
		iterator.next();
		iterator.next();
		assertTrue(iterator.next().getColor().toString().equals(councillor));
		assertTrue(initialCoin + 4 == game.getCurrentPlayer().getCoins());
		initialCoin = game.getCurrentPlayer().getCoins();
		turnHandler = new TurnHandler();
		councillor = game.getFreeCouncillors().getFreeCouncillors().get(0).getColor().toString();
		council = game.getKing().getCouncil();
		councilName = "king";
		action = new ElectCouncillor(councillor, councilName);
		action.doAction(game, turnHandler);
		iterator = council.getCouncil().iterator();
		iterator.next();
		iterator.next();
		iterator.next();
		assertTrue(iterator.next().getColor().toString().equals(councillor));
		assertTrue(initialCoin + 4 == game.getCurrentPlayer().getCoins());	
	}

}
