package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.model.map.CapitalCity;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.FreeCouncillors;
import it.polimi.ingsw.ps23.model.map.GameMap;
import it.polimi.ingsw.ps23.model.map.GroupRegionalCity;
import it.polimi.ingsw.ps23.model.map.InvalidCityException;
import it.polimi.ingsw.ps23.model.map.King;
import it.polimi.ingsw.ps23.model.map.KingTiles;
import it.polimi.ingsw.ps23.model.map.NormalCity;
import it.polimi.ingsw.ps23.model.map.Region;


public class Game {
	
	private GameMap gameMap;
	private Deck politicDeck;
	private Deck permissionDeck;
	private FreeCouncillors freeCouncillors;
	private King king;
	private KingTiles kingTiles;

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
		loadCouncillors();
		loadMap();
		loadPoliticDeck();
		loadPermissionDeck();
		loadKingTiles();
	}

	private void loadMap() {
		CitiesFactory citiesFactory = loadCities();
		CitiesGraph citiesGraph = loadCitiesConnections(citiesFactory.getHashMap());
		List<Region> groupRegionalCities = (ArrayList<Region>) loadRegions(citiesFactory.getHashMap());
		regionalCouncils(groupRegionalCities);
		regionalPermissionCards(groupRegionalCities);
		createKing(citiesFactory.getCities());
		List<Region> groupColoredCities = (ArrayList<Region>) loadColoredRegions(citiesFactory.getCities());
		gameMap = new GameMap(citiesFactory.getCities(), citiesFactory.getHashMap(), citiesGraph, groupRegionalCities, groupColoredCities);
	}

	private CitiesFactory loadCities() {
		List<String[]> rawCities = new RawObject(PATH + CITIES_CSV).getRawObject();
		List<String[]> rawRewardTokens = new RawObject(PATH + REWARD_TOKENS_CSV).getRawObject();
		CitiesFactory citiesFactory = new CitiesFactory();
		citiesFactory.makeCities(rawCities, rawRewardTokens);
		return citiesFactory;
	}
	
	private CitiesGraph loadCitiesConnections(Map<String, City> citiesMap) {
		List<String[]> rawCitiesConnections = new RawObject(PATH + CONNECTIONS_CSV).getRawObject();
		return new CitiesGraph(rawCitiesConnections, citiesMap);
	}

	private List<Region> loadRegions(Map<String, City> citiesMap) {
		List<String[]> rawRegion = new RawObject(PATH + REGIONS).getRawObject();
		return new GroupRegionalCityFactory().makeRegions(rawRegion, citiesMap);
	}
	
	private List<Region> loadColoredRegions(List<City> citiesList) {
		List<String[]> rawColoredCities = new RawObject(PATH + GROUP_COLORED_CSV).getRawObject();
		return new GroupColoredCityFactory().makeGroup(rawColoredCities, citiesList);
	}
	
	
	
	private void loadPoliticDeck() {
		List<String[]> rawPoliticCards = new RawObject(PATH + POLITIC_DECK_CSV).getRawObject();
		politicDeck = new PoliticDeckFactory().makeDeck(rawPoliticCards);	
	}
	
	private void loadPermissionDeck() {
		/*List<String[]> rawPermissionCardsBonuses = new RawObject(PATH + PERMISSION_DECK_BONUSES_CSV).getRawObject();
		List<String[]> rawPermissionCardsCities = new RawObject(PATH + PERMISSION_DECK_CITIES_CSV).getRawObject();
		permissionDeck = new PermissionDeckFactory().makeDeck(rawPermissionCardsBonuses, rawPermissionCardsCities);
		System.out.println("\n" + permissionDeck);*/
	}
	
	private void loadCouncillors() {
		List<String[]> rawCouncillors = new RawObject(PATH + COUNCILLORS_CSV).getRawObject();
		freeCouncillors = new CouncillorsFactory().makeCouncillors(rawCouncillors);
	}

	private void regionalCouncils(List<Region> regions) {
		for (Region region : regions) {
			((GroupRegionalCity) region).setCouncil(new CouncilFactory().makeCouncil(freeCouncillors));
		}
	}
	
	private void regionalPermissionCards(List<Region> regions) {
		for (Region region : regions) {
			//aggiungere le permission card per ogni regione
		}
	}
	
	private void createKing(List<City> cities) {
		for(City city : cities){
			if(city instanceof CapitalCity) {  
				king = new King(city);
			}
		}
	}
	
	private void loadKingTiles() {
		List<String[]> rawKingTiles = new RawObject(PATH + KING_BONUS_TILE_CSV).getRawObject();
		kingTiles =  new KingTileFactory().makeTiles(rawKingTiles); 
	}
	
}
