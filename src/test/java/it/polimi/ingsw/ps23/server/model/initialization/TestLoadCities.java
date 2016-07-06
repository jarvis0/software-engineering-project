package it.polimi.ingsw.ps23.server.model.initialization;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;
import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.server.model.initialization.CitiesBuilder;
import it.polimi.ingsw.ps23.server.model.initialization.GameColorsBuilder;
import it.polimi.ingsw.ps23.server.model.initialization.RawObject;
import it.polimi.ingsw.ps23.server.model.map.CitiesGraph;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.RewardToken;
/**
 * Tests the construction of the {@link City} retrieving data from configuration file and tests all methods of
 * {@link CitiesGraph}, cities connection, cities's hashmap and {@link RewardToken}.
 * @author Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public class TestLoadCities {

	private static final String TEST_CONFIGURATION_PATH = "src/test/java/it/polimi/ingsw/ps23/server/model/initialization/configuration/";
	private static final String CITIES_CSV = "cities.csv";
	private static final String REWARD_TOKENS_CSV = "rewardTokens.csv";
	private static final String CONNECTIONS_CSV = "citiesConnections.csv";
	
	private BonusCache bonusCache;
	private CitiesBuilder citiesBuilder;
	private List<City> cities;
	private int initialCitiesSize;
	private City firstRemovedCity;
	private Map<String, List<String>> citiesConnections;
	
	@Test
	public void test() {		
		List<String[]> rawCities = new RawObject(TEST_CONFIGURATION_PATH + CITIES_CSV).getRawObject();
		List<String[]> rawRewardTokens = new RawObject(TEST_CONFIGURATION_PATH + REWARD_TOKENS_CSV).getRawObject();
		bonusCache = new BonusCache();
		citiesBuilder = new CitiesBuilder();
		citiesBuilder.makeCities(rawCities, rawRewardTokens, bonusCache);
		cities = citiesBuilder.getCities();
		initialCitiesSize = cities.size();		
		citiesTest();
		bonusesTest();
		colorAndNameTest();
		hashMapTest();
		graphTest();
		connectionTest();
	}
	
	private void citiesTest() {	
		firstRemovedCity = cities.remove(initialCitiesSize - 1);
		assertTrue(initialCitiesSize - 1 == cities.size());
		assertTrue(firstRemovedCity.isCapital());
	}
	
	private void bonusesTest() {
		RewardToken rewardToken = new RewardToken();
		rewardToken.addBonus(bonusCache.getBonus("nobilityTrackStep", 1));
		City city = new NormalCity("test", GameColorsBuilder.makeColor("test"), rewardToken);
		assertTrue(((NormalCity) city).hasNobilityTrackBonus());
		rewardToken = new RewardToken();
		rewardToken.addBonus(bonusCache.getBonus("assistant", 1));
		city = new NormalCity("test", GameColorsBuilder.makeColor("test"), rewardToken);
		assertTrue(((NormalCity) city).getRewardToken().getBonuses().containsAll(rewardToken.getBonuses()));
		assertTrue(!((NormalCity) city).hasNobilityTrackBonus());
	}
	
	private void colorAndNameTest () {
		RewardToken rewardToken = new RewardToken();
		rewardToken.addBonus(bonusCache.getBonus("assistant", 1));
		City city = new NormalCity("test", GameColorsBuilder.makeColor("test"), rewardToken);
		assertTrue(city.getColor() == "test");
		assertTrue(city.getName() == "test");
	}
	
	private void hashMapTest() {
		Map<String, City> citiesMap = citiesBuilder.getHashMap();
		assertTrue(citiesMap.containsKey("A"));
		assertTrue(citiesMap.containsKey("B"));
		assertTrue(citiesMap.containsKey("J"));
		assertTrue(citiesMap.get("J").equals(firstRemovedCity));
		assertTrue(citiesMap.get("B").equals(cities.remove(initialCitiesSize - 2)));
		assertTrue(citiesMap.get("A").equals(cities.remove(initialCitiesSize - 3)));
	}
	
	private void graphTest() {
		Map<String, City> citiesMap = citiesBuilder.getHashMap();
		List<String[]> rawCitiesConnections = new RawObject(TEST_CONFIGURATION_PATH + CONNECTIONS_CSV).getRawObject();
		CitiesGraphBuilder citiesGraphBuilder = new CitiesGraphBuilder();
		citiesGraphBuilder.makeCitiesGraph(rawCitiesConnections, citiesMap);
		citiesConnections = citiesGraphBuilder.getCitiesConnections();
		CitiesGraph citiesGraph = citiesGraphBuilder.getCitiesGraph();
		GraphIterator<City, DefaultEdge> iterator = new DepthFirstIterator<>(citiesGraph.getGraph());
		assertTrue(iterator.next().equals(citiesMap.get("A")));
		assertTrue(iterator.next().equals(citiesMap.get("B")));
		assertTrue(iterator.next().equals(citiesMap.get("J")));
	}
	
	private void connectionTest() {
		assertTrue(citiesConnections.get("A").contains("B") && citiesConnections.get("A").size() == 1);
		assertTrue(citiesConnections.get("B").contains("A") && citiesConnections.get("B").contains("J") && citiesConnections.get("B").size() == 2);
		assertTrue(citiesConnections.get("J").contains("B") && citiesConnections.get("J").size() == 1);
	}
	
}
