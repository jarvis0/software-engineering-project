package it.polimi.ingsw.ps23.server.model.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncilException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncillorException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.actions.AssistantToElectCouncillor;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;

public class TestAssistantToElectCouncillor {

	@Test
	public void test() throws InvalidCouncillorException, InvalidCouncilException, InsufficientResourcesException {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		int initialAssistant = game.getCurrentPlayer().getAssistants();
		TurnHandler turnHandler = new TurnHandler();
		String councillor = game.getFreeCouncillors().getFreeCouncillors().get(0).getColorName();
		Council council = ((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(0))).getCouncil();
		String councilName = game.getGameMap().getGroupRegionalCity().get(0).getName();
		AssistantToElectCouncillor action = new AssistantToElectCouncillor(councillor, councilName);
		action.doAction(game, turnHandler);
		assertTrue(!turnHandler.isAvailableQuickAction());
		Iterator<Councillor> iterator = council.getCouncil().iterator();
		iterator.next();
		iterator.next();
		iterator.next();
		assertTrue(iterator.next().getColorName().equals(councillor));
		assertTrue(initialAssistant -1 == game.getCurrentPlayer().getAssistants());
	}

}
