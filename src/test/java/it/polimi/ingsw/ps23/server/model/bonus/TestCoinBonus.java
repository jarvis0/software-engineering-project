package it.polimi.ingsw.ps23.server.model.bonus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
/**
  * Tests all the changes made by {@link CoinBonus} to the {@link Player} status.
 * @author Mirco Manzoni
 *
 */
public class TestCoinBonus {

	@Test
	public void test() {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		TurnHandler turnHandler = new TurnHandler();
		CoinBonus bonus = new CoinBonus("Coin Bonus");
		int initialCoins = game.getCurrentPlayer().getCoins();
		bonus.updateBonus(game, turnHandler);
		assertTrue(game.getCurrentPlayer().getCoins() == initialCoins + bonus.getValue());
	}

}
