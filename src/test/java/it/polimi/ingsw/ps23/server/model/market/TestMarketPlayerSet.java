package it.polimi.ingsw.ps23.server.model.market;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;
/**
 * Tests the {@link MarketPlayerSet}. In particular the handler of players during market phase and when
 * a disconnection occurred during the market.
 * @author Mirco Manzoni
 *
 */
public class TestMarketPlayerSet {

	@Test
	public void test() {
		MarketPlayersSet marketPlayersSet = new MarketPlayersSet();
		List<Player> players =new ArrayList<>();
		int i;
		for(i = 0; i < 10; i++) {
			Player player = new Player(String.valueOf(i), 0, 0, new PoliticHandDeck(new ArrayList<Card>()));
			marketPlayersSet.addPlayer(player);
			players.add(player);
		}
		marketPlayersSet.shufflePlayers();
		assertTrue(marketPlayersSet.getPlayers().size() == i);
		boolean found = false;
		for(i = 0; i < 10; i++) {
			if(!players.get(i).equals(marketPlayersSet.getPlayers().get(i))) {
				found = true;
			}
		}
		assertTrue(found);
		for(i = 0; i < 10; i++) {
			assertTrue(players.contains(marketPlayersSet.getCurrentPlayer()));
		}
		assertTrue(marketPlayersSet.isEmpty());
	}

}
