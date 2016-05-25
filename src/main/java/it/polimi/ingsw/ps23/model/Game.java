package it.polimi.ingsw.ps23.model;

import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.FreeCouncillors;
import it.polimi.ingsw.ps23.model.map.GameMap;
import it.polimi.ingsw.ps23.model.map.GroupRegionalCity;
import it.polimi.ingsw.ps23.model.map.Region;


public class Game {
	
	private GameMap gameMap;
	private Deck politicDeck;
	private FreeCouncillors freeCouncillors;

	private static final String PATH = "src/main/java/it/polimi/ingsw/ps23/csv/";
	private static final String CITIES_CSV = "cities.csv";
	private static final String CONNECTIONS_CSV = "citiesConnections.csv";
	private static final String COUNCILLORS_CSV = "councillors.csv";
	private static final String PERMISSION_DECK_CSV = "permissionDeck.csv";
	private static final String POLITIC_DECK_CSV = "politicDeck.csv";
	private static final String REWARD_TOKENS_CSV = "rewardTokens.csv";
	private static final String REGIONS = "regions.csv";
	private static final String GROUP_COLORED_CSV = "groupColoredCitiesBonusTiles.csv";
	private static final String KING_BONUS_TILE_CSV = "kingBonusTiles.csv";
	
	public Game() {
		loadPoliticDeck();
		loadCouncillors();
		loadMap();
	}
	
	private void loadPoliticDeck() {
		List<String[]> rawPoliticCards = new RawObject(PATH + POLITIC_DECK_CSV).getRawObject();
		politicDeck = new PoliticDeckFactory().makeDeck(rawPoliticCards);	
	}
	
	private void loadCouncillors() {
		List<String[]> rawCouncillors = new RawObject(PATH + COUNCILLORS_CSV).getRawObject();
		freeCouncillors = new CouncillorsFactory().makeCouncillors(rawCouncillors);
	}
	
	private CitiesFactory loadCities() {
		List<String[]> rawCities = new RawObject(PATH + CITIES_CSV).getRawObject();
		List<String[]> rawRewardTokens = new RawObject(PATH + REWARD_TOKENS_CSV).getRawObject();
		CitiesFactory citiesFactory = new CitiesFactory();
		citiesFactory.makeCities(rawCities, rawRewardTokens);
		return citiesFactory;
	}
	
	private CitiesGraph loadCitiesConnections(Map<String, City> cities) {
		List<String[]> rawCitiesConnections = new RawObject(PATH + CONNECTIONS_CSV).getRawObject();
		return new CitiesGraph(rawCitiesConnections, cities);
	}
	
	private List<Region> loadRegions(Map<String, City> cities) {
		List<String[]> rawRegion = new RawObject(PATH + REGIONS).getRawObject();
		return new GroupRegionalCityFactory().makeRegions(rawRegion, cities);
	}

	private void regionalCouncils(List<Region> regions) {
		for(Region region : regions) {
			((GroupRegionalCity) region).setCouncil(new CouncilFactory().makeCouncil(freeCouncillors));
		}
	}

	private Map<String, Deck> loadPermissionDecks(Map<String, City> cities, List<Region> regions) {
		List<String[]> rawPermissionCards = new RawObject(PATH + PERMISSION_DECK_CSV).getRawObject();
		return new PermissionDecksFactory().makeDecks(rawPermissionCards, cities);
	}
	
	private void regionalPermissionDecks(Map<String, City> cities, List<Region> regions) {
		Map<String, Deck> permissionDeck = loadPermissionDecks(cities, regions);
		for(Region region : regions) {
			((GroupRegionalCity) region).setPermissionDeck(permissionDeck.get(region.getId()));
		}
	}

	private List<Region> loadColoredRegions(List<City> cities) {
		List<String[]> rawColoredCities = new RawObject(PATH + GROUP_COLORED_CSV).getRawObject();
		return new GroupColoredCityFactory().makeGroup(rawColoredCities, cities);
	}

	private void loadMap() {
		CitiesFactory citiesFactory = loadCities();
		Map<String, City> citiesMap = citiesFactory.getHashMap();
		CitiesGraph citiesGraph = loadCitiesConnections(citiesMap);
		List<Region> groupRegionalCities = loadRegions(citiesMap);
		regionalCouncils(groupRegionalCities);
		regionalPermissionDecks(citiesMap, groupRegionalCities);
		List<Region> groupColoredCities = loadColoredRegions(citiesFactory.getCities());
		gameMap = new GameMap(citiesFactory.getCities(), citiesFactory.getHashMap(), citiesGraph, groupRegionalCities, groupColoredCities);
		System.out.println(gameMap);
	}

}
