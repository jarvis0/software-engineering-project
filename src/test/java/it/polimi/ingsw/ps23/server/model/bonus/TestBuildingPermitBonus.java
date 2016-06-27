package it.polimi.ingsw.ps23.server.model.bonus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class TestBuildingPermitBonus {

	@Test
	public void test() {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		TurnHandler turnHandler = new TurnHandler();
		BuildingPermitBonus bonus = new BuildingPermitBonus("Building Permit Bonus");
		bonus.updateBonus(game, turnHandler);
		assertTrue(turnHandler.getSuperBonuses().contains(bonus));
		//bonus.acquireSuperBonus(input, game, turnHandler);
	}

}
