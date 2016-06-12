package it.polimi.ingsw.ps23.initialization;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import it.polimi.ingsw.ps23.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.model.initialization.CitiesFactory;
import it.polimi.ingsw.ps23.model.initialization.GameColorFactory;
import it.polimi.ingsw.ps23.model.initialization.RawObject;
import it.polimi.ingsw.ps23.model.map.regions.CapitalCity;
import it.polimi.ingsw.ps23.model.map.regions.City;
import it.polimi.ingsw.ps23.model.map.regions.NormalCity;
import it.polimi.ingsw.ps23.model.map.regions.RewardToken;

public class TestLoadCities {

	private static final String TEST_CONFIGURATION_PATH = "src/test/java/configuration/";
	private static final String CITIES_CSV = "cities.csv";
	private static final String REWARD_TOKENS_CSV = "rewardTokens.csv";
	
	@Test
	public void test() {		
		List<String[]> rawCities = new RawObject(TEST_CONFIGURATION_PATH + CITIES_CSV).getRawObject();
		List<String[]> rawRewardTokens = new RawObject(TEST_CONFIGURATION_PATH + REWARD_TOKENS_CSV).getRawObject();
		CitiesFactory citiesFactory = new CitiesFactory();
		citiesFactory.makeCities(rawCities, rawRewardTokens);
		List<City> cities = citiesFactory.getCities();
		int n = cities.size();
		City city1 = cities.remove(n - 1);
		assertTrue(n - 1 == cities.size());
		assertTrue(city1 instanceof CapitalCity);
		BonusCache.loadCache();
		RewardToken rewardToken = new RewardToken();
		rewardToken.addBonus(BonusCache.getBonus("nobilityTrackStep", 1));
		City city = new NormalCity("test", GameColorFactory.makeColor("test", "0xFFFFFF"), rewardToken);
		assertTrue(((NormalCity) city).hasNobilityTrackBonus());
		rewardToken = new RewardToken();
		rewardToken.addBonus(BonusCache.getBonus("assistant", 1));
		city = new NormalCity("test", GameColorFactory.makeColor("test", "0xFFFFFF"), rewardToken);
		assertTrue(!((NormalCity) city).hasNobilityTrackBonus());
		assertTrue(city.getColor() == "test");
		assertTrue(city.getName() == "test");
		Map<String, City> citiesMap = citiesFactory.getHashMap();
		assertTrue(citiesMap.containsKey("A"));
		assertTrue(citiesMap.containsKey("B"));
		assertTrue(citiesMap.containsKey("J"));
		assertTrue(citiesMap.get("J").equals(city1));
		assertTrue(citiesMap.get("B").equals(cities.remove(n - 2)));
		assertTrue(citiesMap.get("A").equals(cities.remove(n - 3)));
	}

}
