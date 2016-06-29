package it.polimi.ingsw.ps23.server.model.market;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;

public class TestMarket {

	@Test
	public void test() {
		String playerName = "a";
		MarketPlayersSet marketPlayersSet = new MarketPlayersSet();
		Player player = new Player("a", 0, 0, new PoliticHandDeck(new ArrayList<Card>()));
		marketPlayersSet.addPlayer(player);
		Market market = new Market(marketPlayersSet);
		assertTrue(market.continueMarket());
		assertTrue(player.equals(market.selectPlayer()));
		List<Integer> permissionCards = new ArrayList<>();
		permissionCards.add(0);
		permissionCards.add(1);
		List<String> politicCards = new ArrayList<>();
		politicCards.add("white");
		politicCards.add("blue");
		int assistants = 2;
		int cost = 10;
		MarketObject marketObject = new MarketObject(playerName, permissionCards, politicCards, assistants, cost);
		market.addMarketObject(marketObject);
		assertTrue(market.getMarketObject().contains(marketObject));
		assertTrue(market.sellObjects() == 1);
		market.remove(marketObject);
		assertTrue(market.sellObjects() == 0);
		assertFalse(market.continueMarket());
		Player player2 = new Player("b", 0, 0, new PoliticHandDeck(new ArrayList<Card>()));
		Player player3 = new Player("b", 0, 0, new PoliticHandDeck(new ArrayList<Card>()));
		marketPlayersSet.addPlayer(player2);
		marketPlayersSet.addPlayer(player3);
		market = new Market(marketPlayersSet);
		player2.setOnline(false);
		player3.setOnline(false);
		assertTrue(player.equals(market.selectPlayer()));		
	}

}
