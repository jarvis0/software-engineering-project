package it.polimi.ingsw.ps23.server.model.market;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
/**
 * Tests the construction of {@link MarketObject}.
 * @author Mirco Manzoni
 *
 */
public class TestMarketObject {

	@Test
	public void test() {
		String player = "a";
		List<Integer> permissionCards = new ArrayList<>();
		permissionCards.add(0);
		permissionCards.add(1);
		List<String> politicCards = new ArrayList<>();
		politicCards.add("white");
		politicCards.add("blue");
		int assistants = 2;
		int cost = 10;
		MarketObject marketObject =new MarketObject(player, permissionCards, politicCards, assistants, cost);
		assertTrue(marketObject.getPlayer().equals(player));
		assertTrue(marketObject.getPermissionCards().containsAll(permissionCards));
		assertTrue(marketObject.getPoliticCards().containsAll(politicCards));
		assertTrue(marketObject.getAssistants() == assistants);
		assertTrue(marketObject.getCost() == cost);
	}

}
