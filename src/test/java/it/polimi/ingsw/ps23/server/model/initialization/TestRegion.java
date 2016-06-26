package it.polimi.ingsw.ps23.server.model.initialization;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncillorException;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.board.FreeCouncillorsSet;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;

public class TestRegion {

	private static final String TEST_CONFIGURATION_PATH = "src/test/java/it/polimi/ingsw/ps23/server/model/initialization/configuration/";
	private static final String CITIES_CSV = "cities.csv";
	private static final String REWARD_TOKENS_CSV = "rewardTokens.csv";
	private static final String CONNECTIONS_CSV = "citiesConnections.csv";
	private static final String REGIONS_CSV = "regions.csv";
	private static final String COUNCILLORS_CSV = "councillors.csv";
	private static final String PERMISSION_DECK_CSV = "permissionDecks.csv";
	
	@Test//TODO manca da verificare quando un deck viene reinizializzato
	public void test() {
		BonusCache bonusCache = new BonusCache();
		List<String[]> rawCities = new RawObject(TEST_CONFIGURATION_PATH + CITIES_CSV).getRawObject();
		List<String[]> rawRewardTokens = new RawObject(TEST_CONFIGURATION_PATH + REWARD_TOKENS_CSV).getRawObject();
		CitiesFactory citiesFactory = new CitiesFactory();
		citiesFactory.makeCities(rawCities, rawRewardTokens, bonusCache);
		List<String[]> rawCitiesConnections = new RawObject(TEST_CONFIGURATION_PATH + CONNECTIONS_CSV).getRawObject();
		CitiesGraphFactory citiesGraphFactory = new CitiesGraphFactory();
		citiesGraphFactory.makeCitiesGraph(rawCitiesConnections, citiesFactory.getHashMap());
		List<String[]> rawRegions = new RawObject(TEST_CONFIGURATION_PATH + REGIONS_CSV).getRawObject();
		List<Region> regions = new GroupRegionalCitiesFactory().makeRegions(rawRegions, citiesFactory.getHashMap(), citiesGraphFactory.getCitiesConnections());
		assertTrue(regions.get(0).getName().equals("seaside"));
		List<String[]> rawCouncillors = new RawObject(TEST_CONFIGURATION_PATH + COUNCILLORS_CSV).getRawObject();
		FreeCouncillorsSet freeCouncillors = new CouncillorsFactory().makeCouncillors(rawCouncillors);
		((GroupRegionalCity) regions.get(0)).setCouncil(new CouncilFactory().makeCouncil(freeCouncillors));
		GameColor blue = GameColorFactory.makeColor("blue");
		Councillor councillor = new Councillor(blue);
		Iterator<Councillor> iterator = ((GroupRegionalCity) regions.get(0)).getCouncil().getCouncil().iterator();
		assertTrue(iterator.next().equals(((GroupRegionalCity) regions.get(0)).getCouncil().pushCouncillor(councillor)));
		assertTrue(((GroupRegionalCity) regions.get(0)).getCouncil().getCouncil().contains(councillor));
		((GroupRegionalCity) regions.get(0)).getCouncil().pushCouncillor(councillor);
		((GroupRegionalCity) regions.get(0)).getCouncil().pushCouncillor(councillor);
		((GroupRegionalCity) regions.get(0)).getCouncil().pushCouncillor(councillor);
		iterator = ((GroupRegionalCity) regions.get(0)).getCouncil().getCouncil().iterator();
		while (iterator.hasNext()) {
			assertTrue(iterator.next().equals(councillor));			
		}
		Bonus bonusTile = regions.get(0).acquireBonusTile();
		assertTrue(bonusTile.getName().equals("victoryPoint") && bonusTile.getValue() == 5);
		List<String[]> rawPermissionCards = new RawObject(TEST_CONFIGURATION_PATH + PERMISSION_DECK_CSV).getRawObject();
		Map<String, Deck> deck = new PermissionDecksFactory(rawPermissionCards, citiesFactory.getHashMap()).makeDecks(bonusCache);
		for(Region region : regions) {
			((GroupRegionalCity) region).setPermissionDeck(deck.get(region.getName()));
		}
		List<Card> deckUp = new ArrayList<>();
		for(Card card : ((GroupRegionalCity) regions.get(0)).getPermissionDeckUp().getCards()) {
			deckUp.add(card);
		}
		((GroupRegionalCity) regions.get(0)).changePermitTiles();
		boolean changed = true;
		List<Card> cards = ((GroupRegionalCity) regions.get(0)).getPermissionDeckUp().getCards();
		for(Card card : cards) {
			for(Card oldCard : deckUp) {
				if(card.equals(oldCard)) {
					changed = false;
				}
			}
		}
		assertTrue(changed);
		deckUp.clear();
		deckUp.add(((GroupRegionalCity)regions.get(0)).getPermissionDeckUp().getCards().get(0));
		assertTrue(((GroupRegionalCity)regions.get(0)).pickPermissionCard(0).equals(deckUp.get(0)));
		assertTrue(!((GroupRegionalCity)regions.get(0)).getPermissionDeckUp().getCards().get(0).equals(deckUp.get(0)));
		for(int i = 0; i < 4; i++) {
			try {
				freeCouncillors.electCouncillor("orange", ((GroupRegionalCity)regions.get(0)).getCouncil());
			} catch (InvalidCouncillorException e) { }
		}
		iterator = ((GroupRegionalCity)regions.get(0)).getCouncil().getCouncil().iterator();
		while(iterator.hasNext()) {
			assertTrue(iterator.next().toString().equals("orange"));
		}
	}

}
