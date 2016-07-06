package it.polimi.ingsw.ps23.server.model.bonus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidRegionException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;

public class TestBuildingPermitBonus {

	@Test
	public void test() throws InvalidRegionException {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		TurnHandler turnHandler = new TurnHandler();
		BuildingPermitBonus bonus = new BuildingPermitBonus("Building Permit Bonus");
		bonus.updateBonus(game, turnHandler);
		assertTrue(turnHandler.getSuperBonuses().contains(bonus));
		String checkBonus = bonus.checkBonus(game.getCurrentPlayer());
		Card card = ((GroupRegionalCity) game.getGameMap().getGroupRegionalCity().get(0)).getPermitTilesUp().getCards().get(0);
		bonus.selectRegion(game.getGameMap().getGroupRegionalCity().get(0).getName());
		assertFalse(checkBonus.equals(bonus.checkBonus(game.getCurrentPlayer())));
		List<String> input = new ArrayList<>();
		input.add(game.getGameMap().getGroupRegionalCity().get(0).getName());
		input.add(String.valueOf(1));
		bonus.acquireSuperBonus(input, game, turnHandler);
		assertTrue(game.getCurrentPlayer().getPermitHandDeck().getCards().contains(card));
	}

}
