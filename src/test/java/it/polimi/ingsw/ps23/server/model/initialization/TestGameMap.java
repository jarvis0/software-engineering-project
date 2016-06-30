package it.polimi.ingsw.ps23.server.model.initialization;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.GameMap;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;

public class TestGameMap {

	private static final String TEST_CONFIGURATION_PATH = "src/test/java/it/polimi/ingsw/ps23/server/model/initialization/configuration/";
	private static final String CITIES_CSV = "cities.csv";
	private static final String REWARD_TOKENS_CSV = "rewardTokens.csv";
	private static final String CONNECTIONS_CSV = "citiesConnections.csv";
	private static final String REGIONS_CSV = "regions.csv";
	private static final String PERMISSION_DECK_CSV = "permissionDecks.csv";
	private static final String GROUP_COLORED_CSV = "groupColoredCitiesBonusTiles.csv";
	
	@Test
	public void test() {
		BonusCache bonusCache = new BonusCache();
		List<String[]> rawCities = new RawObject(TEST_CONFIGURATION_PATH + CITIES_CSV).getRawObject();
		List<String[]> rawRewardTokens = new RawObject(TEST_CONFIGURATION_PATH + REWARD_TOKENS_CSV).getRawObject();
		CitiesFactory citiesFactory = new CitiesFactory();
		citiesFactory.makeCities(rawCities, rawRewardTokens, bonusCache);
		List<String[]> rawCitiesConnections = new RawObject(TEST_CONFIGURATION_PATH + CONNECTIONS_CSV).getRawObject();
		CitiesGraphBuilder citiesGraphFactory = new CitiesGraphBuilder();
		citiesGraphFactory.makeCitiesGraph(rawCitiesConnections, citiesFactory.getHashMap());
		List<String[]> rawRegions = new RawObject(TEST_CONFIGURATION_PATH + REGIONS_CSV).getRawObject();
		List<Region> groupRegional = new GroupRegionalCitiesBuilder().makeRegions(rawRegions, citiesFactory.getHashMap(), citiesGraphFactory.getCitiesConnections());
		List<String[]> rawColoredCities = new RawObject(TEST_CONFIGURATION_PATH + GROUP_COLORED_CSV).getRawObject();
		List<Region> groupColored = new GroupColoredCitiesBuilder().makeGroup(rawColoredCities, citiesFactory.getCities());
		List<String[]> rawPermissionCards = new RawObject(TEST_CONFIGURATION_PATH + PERMISSION_DECK_CSV).getRawObject();
		Map<String, Deck> deck = new PermitTilesBuilder(rawPermissionCards, citiesFactory.getHashMap()).makeDecks(bonusCache);
		for(Region region : groupRegional) {
			((GroupRegionalCity) region).setPermitTiles(deck.get(region.getName()));
		}
		GameMap gameMap = new GameMap(citiesFactory.getHashMap(), citiesGraphFactory.getCitiesGraph(), groupRegional, groupColored);
		assertTrue(citiesGraphFactory.getCitiesGraph().equals(gameMap.getCitiesGraph()));
		assertTrue(citiesFactory.getHashMap().equals(gameMap.getCities()));
		assertTrue(groupRegional.equals(gameMap.getGroupRegionalCity()));
		assertTrue(groupRegional.get(0).equals(gameMap.getRegion("seaside")));
		assertTrue(groupRegional.get(0).equals(gameMap.getRegionMap().get("seaside")));
		assertTrue(((GroupRegionalCity)groupRegional.get(0)).getPermitTilesUp().equals(gameMap.getPermissionCardsUp().get("seaside")));		
	}

}
