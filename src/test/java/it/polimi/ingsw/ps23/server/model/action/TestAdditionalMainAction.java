package it.polimi.ingsw.ps23.server.model.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.actions.AdditionalMainAction;

public class TestAdditionalMainAction {

	@Test
	public void test() throws InsufficientResourcesException {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		game.getCurrentPlayer().updateAssistants(2);
		int initialAssistant = game.getCurrentPlayer().getAssistants();
		TurnHandler turnHandler = new TurnHandler();
		AdditionalMainAction action = new AdditionalMainAction();
		turnHandler.useMainAction();
		assertTrue(!turnHandler.isAvailableMainAction());
		action.doAction(game, turnHandler);		
		assertTrue(!turnHandler.isAvailableQuickAction() && turnHandler.isAvailableMainAction());
		assertTrue(game.getCurrentPlayer().getAssistants() == initialAssistant - 3);
	}

}
