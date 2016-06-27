package it.polimi.ingsw.ps23.server.model.bonus;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.player.Player;

public class TestNullBonus {

	@Test
	public void test() {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		TurnHandler turnHandler = new TurnHandler();
		NullBonus bonus = new NullBonus("Null Bonus");
		Player player = new Player(game.getCurrentPlayer().getName(), game.getCurrentPlayer().getCoins(), game.getCurrentPlayer().getAssistants(), game.getCurrentPlayer().getPoliticHandDeck());
		bonus.updateBonus(game, turnHandler);
		assertTrue(game.getCurrentPlayer().getName().equals(player.getName()));
		assertTrue(game.getCurrentPlayer().getCoins() == player.getCoins());
		assertTrue(game.getCurrentPlayer().getEmporiums().getBuiltEmporiumsSet().size() == player.getEmporiums().getBuiltEmporiumsSet().size());
		assertTrue(game.getCurrentPlayer().getAssistants() == player.getAssistants());
		assertTrue(game.getCurrentPlayer().getVictoryPoints() == player.getVictoryPoints());
		assertTrue(game.getCurrentPlayer().getNobilityTrackPoints() == player.getNobilityTrackPoints());
		assertTrue(game.getCurrentPlayer().isOnline() == player.isOnline());
		assertTrue(game.getCurrentPlayer().getPoliticHandDeck().getCards().containsAll(player.getPoliticHandDeck().getCards()));
		assertTrue(game.getCurrentPlayer().getPermissionHandDeck().getCards().containsAll(player.getPermissionHandDeck().getCards()));
	}

}
