package it.polimi.ingsw.ps23.server.model.initialization;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.server.model.bonus.NobilityTrackStepBonus;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
import it.polimi.ingsw.ps23.server.model.map.regions.CapitalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.PermissionCard;

public class TestLoadPermissionDeck {

	private static final String TEST_CONFIGURATION_PATH = "src/test/java/it/polimi/ingsw/ps23/server/model/initialization/configuration/";
	private static final String PERMISSION_DECK_CSV = "permissionDecks.csv";
	
	private static final String NOBILITY_TRACK_STEP = "nobilityTrackStep";
	
	@Test
	public void test() {
		List<String[]> rawPermissionCards = new RawObject(TEST_CONFIGURATION_PATH + PERMISSION_DECK_CSV).getRawObject();
		GameColor iron = GameColorFactory.makeColor("iron");
		City city = new CapitalCity("A",iron);
		Map<String, City> cities = new HashMap<>();
		cities.put(city.getName(), city);
		city = new CapitalCity("B",iron);
		cities.put(city.getName(), city);
		city = new CapitalCity("J",iron);
		cities.put(city.getName(), city);
		Map<String, Deck> deck = new PermissionDecksFactory(rawPermissionCards, cities).makeDecks(new BonusCache());
		Card permissionCard = new PermissionCard();
		((PermissionCard)permissionCard).addCity(city);
		assertTrue(((PermissionCard)permissionCard).containCity(city));
		Bonus bonus = new NobilityTrackStepBonus(NOBILITY_TRACK_STEP);
		bonus.setValue(1);
		((PermissionCard)permissionCard).addBonus(bonus);
		boolean found = false;
		for(Card card : deck.get("seaside").getCards()) {
			if (card.toString().equals(permissionCard.toString())) {
				found = true;
			}
		}
		assertTrue(found);
	}

}
