package it.polimi.ingsw.ps23.model;

import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.model.map.NobilityTrack;
import it.polimi.ingsw.ps23.model.map.CapitalCity;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.FreeCouncillors;
import it.polimi.ingsw.ps23.model.map.GameMap;
import it.polimi.ingsw.ps23.model.map.GroupRegionalCity;
import it.polimi.ingsw.ps23.model.map.King;
import it.polimi.ingsw.ps23.model.map.KingTiles;
import it.polimi.ingsw.ps23.model.map.Region;

public class Initialization {
	
	//è corretto organizzare così le costanti?
	private static final String PATH = "src/main/java/it/polimi/ingsw/ps23/csv/";
	private static final String CITIES_CSV = "cities.csv";
	private static final String CONNECTIONS_CSV = "citiesConnections.csv";
	private static final String COUNCILLORS_CSV = "councillors.csv";
	private static final String PERMISSION_DECK_CSV = "permissionDeck.csv";
	private static final String POLITIC_DECK_CSV = "politicDeck.csv";
	private static final String REWARD_TOKENS_CSV = "rewardTokens.csv";
	private static final String REGIONS_CSV = "regions.csv";
	private static final String GROUP_COLORED_CSV = "groupColoredCitiesBonusTiles.csv";
	private static final String KING_BONUS_TILE_CSV = "kingBonusTiles.csv";
	private static final String NOBILY_TRACK_CSV = "nobilityTrack.csv";
	
	private static final int STARTING_COINS = 10;
	private static final int STARTING_ASSISTANT = 1;
	private static final int STARTING_POLITIC_CARDS_NUMBER = 6;
	
	private Deck politicDeck;
	private FreeCouncillors freeCouncillors;
	private GameMap gameMap;
	private King king;
	private KingTiles kingTiles;
	private NobilityTrack nobilityTrack;
	private GamePlayers gamePlayers;
	
	Initialization(List<String> playersID) throws NoCapitalException {
		loadPoliticDeck();
		loadCouncillors();
		loadMap();
		king = createKing();
		loadKingTiles();
		loadNobilityTrack();
		loadPlayers(playersID);
		System.out.println(gameMap.getGroupColoredCity());
	}
	
	Deck getPoliticDeck() {
		return politicDeck;
	}

	FreeCouncillors getFreeCouncillors() {
		return freeCouncillors;
	}

	GameMap getGameMap() {
		return gameMap;
	}

	King getKing() {
		return king;
	}

	KingTiles getKingTiles() {
		return kingTiles;
	}

	NobilityTrack getNobilityTrack() {
		return nobilityTrack;
	}

	GamePlayers getGamePlayers() {
		return gamePlayers;
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
		return new CitiesGraphFactory().makeCitiesGraph(rawCitiesConnections, cities);
	}

	private List<Region> loadRegions(Map<String, City> citiesMap) {
		List<String[]> rawRegions = new RawObject(PATH + REGIONS_CSV).getRawObject();
		return new GroupRegionalCityFactory().makeRegions(rawRegions, citiesMap);
	}
	
	private void regionalCouncils(List<Region> regions) {
		for(Region region : regions) {
			((GroupRegionalCity) region).setCouncil(new CouncilFactory().makeCouncil(freeCouncillors));
		}
	}

	private Map<String, Deck> loadPermissionDecks(Map<String, City> cities) {
		List<String[]> rawPermissionCards = new RawObject(PATH + PERMISSION_DECK_CSV).getRawObject();
		return new PermissionDecksFactory(rawPermissionCards, cities).makeDecks();
	}
	
	private void regionalPermissionDecks(Map<String, City> cities, List<Region> regions) {
		Map<String, Deck> permissionDeck = loadPermissionDecks(cities);
		for(Region region : regions) {
			((GroupRegionalCity) region).setPermissionDeck(permissionDeck.get(region.getID()));
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
		gameMap = new GameMap(citiesFactory.getCities(), citiesMap, citiesGraph, groupRegionalCities, groupColoredCities);
	}
	
	private King createKing() throws NoCapitalException {
		List<City> cities = gameMap.getCitiesList();
		for(City city : cities){
			if(city instanceof CapitalCity) {  
				return new King(city, new CouncilFactory().makeCouncil(freeCouncillors));
			}
		}
		throw new NoCapitalException();
	}
	
	private void loadKingTiles() {
		List<String[]> rawKingTiles = new RawObject(PATH + KING_BONUS_TILE_CSV).getRawObject();
		kingTiles = new KingTileFactory().makeTiles(rawKingTiles); 
	}
	
	private void loadNobilityTrack() {
		List<String[]> rawNobilityTrackSteps = new RawObject(PATH + NOBILY_TRACK_CSV).getRawObject();
		nobilityTrack = new NobilityTrackFactory().makeNobilityTrack(rawNobilityTrackSteps);
	}

	private void loadPlayers(List<String> playersID) {
		gamePlayers = new GamePlayers();
		int playersNumber = playersID.size();
		for(int i = 0; i < playersNumber; i++) {
			gamePlayers.addPlayer(new Player(playersID.get(i), STARTING_COINS + i, STARTING_ASSISTANT + i, new PoliticHandDeck(politicDeck.pickCards(STARTING_POLITIC_CARDS_NUMBER))));
		}
	}
}
