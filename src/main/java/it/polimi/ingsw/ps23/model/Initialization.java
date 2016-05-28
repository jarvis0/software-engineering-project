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
	private static final String CONFIGURATIONS_PATH = "src/main/java/it/polimi/ingsw/ps23/csv/";
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
	private static final int STARTING_ASSISTANTS = 1;
	private static final int STARTING_POLITIC_CARDS_NUMBER = 6;
	
	private Deck politicDeck;
	private FreeCouncillors freeCouncillors;
	private GameMap gameMap;
	private King king;
	private KingTiles kingTiles;
	private NobilityTrack nobilityTrack;
	private GamePlayerSet gamePlayerSet;
	
	public Initialization(List<String> playersName) throws NoCapitalException {
		loadPoliticDeck();
		loadCouncillors();
		loadMap();
		createKing();
		loadKingTiles();
		loadNobilityTrack();
		loadPlayers(playersName);
	}
	
	public Deck getPoliticDeck() {
		return politicDeck;
	}

	public FreeCouncillors getFreeCouncillors() {
		return freeCouncillors;
	}

	public GameMap getGameMap() {
		return gameMap;
	}

	public King getKing() {
		return king;
	}

	public KingTiles getKingTiles() {
		return kingTiles;
	}

	public NobilityTrack getNobilityTrack() {
		return nobilityTrack;
	}

	public GamePlayerSet getGamePlayerSet() {
		return gamePlayerSet;
	}
	
	private void loadPoliticDeck() {
		List<String[]> rawPoliticCards = new RawObject(CONFIGURATIONS_PATH + POLITIC_DECK_CSV).getRawObject();
		politicDeck = new PoliticDeckFactory().makeDeck(rawPoliticCards);	
	}
	
	private void loadCouncillors() {
		List<String[]> rawCouncillors = new RawObject(CONFIGURATIONS_PATH + COUNCILLORS_CSV).getRawObject();
		freeCouncillors = new CouncillorsFactory().makeCouncillors(rawCouncillors);
	}
	
	private CitiesFactory loadCities() {
		List<String[]> rawCities = new RawObject(CONFIGURATIONS_PATH + CITIES_CSV).getRawObject();
		List<String[]> rawRewardTokens = new RawObject(CONFIGURATIONS_PATH + REWARD_TOKENS_CSV).getRawObject();
		CitiesFactory citiesFactory = new CitiesFactory();
		citiesFactory.makeCities(rawCities, rawRewardTokens);
		return citiesFactory;
	}
	
	private CitiesGraph loadCitiesConnections(Map<String, City> cities) {
		List<String[]> rawCitiesConnections = new RawObject(CONFIGURATIONS_PATH + CONNECTIONS_CSV).getRawObject();
		return new CitiesGraphFactory().makeCitiesGraph(rawCitiesConnections, cities);
	}

	private List<Region> loadRegions(Map<String, City> citiesMap) {
		List<String[]> rawRegions = new RawObject(CONFIGURATIONS_PATH + REGIONS_CSV).getRawObject();
		return new GroupRegionalCitiesFactory().makeRegions(rawRegions, citiesMap);
	}
	
	private void regionalCouncils(List<Region> regions) {
		for(Region region : regions) {
			((GroupRegionalCity) region).setCouncil(new CouncilFactory().makeCouncil(freeCouncillors));
		}
	}

	private Map<String, Deck> loadPermissionDecks(Map<String, City> cities) {
		List<String[]> rawPermissionCards = new RawObject(CONFIGURATIONS_PATH + PERMISSION_DECK_CSV).getRawObject();
		return new PermissionDecksFactory(rawPermissionCards, cities).makeDecks();
	}
	
	private void regionalPermissionDecks(Map<String, City> cities, List<Region> regions) {
		Map<String, Deck> permissionDeck = loadPermissionDecks(cities);
		for(Region region : regions) {
			((GroupRegionalCity) region).setPermissionDeck(permissionDeck.get(region.getName()));
		}
	}

	private List<Region> loadColoredRegions(List<City> cities) {
		List<String[]> rawColoredCities = new RawObject(CONFIGURATIONS_PATH + GROUP_COLORED_CSV).getRawObject();
		return new GroupColoredCitiesFactory().makeGroup(rawColoredCities, cities);
	}

	private void loadMap() {
		CitiesFactory citiesFactory = loadCities();
		List<City> citiesList = citiesFactory.getCities();
		Map<String, City> citiesMap = citiesFactory.getHashMap();
		CitiesGraph citiesGraph = loadCitiesConnections(citiesMap);
		List<Region> groupRegionalCities = loadRegions(citiesMap);
		//hashmap regions
		regionalCouncils(groupRegionalCities);
		regionalPermissionDecks(citiesMap, groupRegionalCities);
		List<Region> groupColoredCities = loadColoredRegions(citiesList);
		gameMap = new GameMap(citiesList, citiesMap, citiesGraph, groupRegionalCities, groupColoredCities);
	}
	
	private void createKing() throws NoCapitalException {
		List<City> cities = gameMap.getCitiesList();
		for(City city : cities){
			if(city instanceof CapitalCity) {  
				king = new King(city, new CouncilFactory().makeCouncil(freeCouncillors));
				return;
			}
		}
		throw new NoCapitalException();
	}
	
	private void loadKingTiles() {
		List<String[]> rawKingTiles = new RawObject(CONFIGURATIONS_PATH + KING_BONUS_TILE_CSV).getRawObject();
		kingTiles = new KingTileFactory().makeTiles(rawKingTiles); 
	}
	
	private void loadNobilityTrack() {
		List<String[]> rawNobilityTrackSteps = new RawObject(CONFIGURATIONS_PATH + NOBILY_TRACK_CSV).getRawObject();
		nobilityTrack = new NobilityTrackFactory().makeNobilityTrack(rawNobilityTrackSteps);
	}

	private void loadPlayers(List<String> playersID) {
		gamePlayerSet = new GamePlayerSet();
		int playersNumber = playersID.size();
		for(int i = 0; i < playersNumber; i++) {
			gamePlayerSet.addPlayer(new Player(playersID.get(i), STARTING_COINS + i, STARTING_ASSISTANTS + i, new PoliticHandDeck(politicDeck.pickCards(STARTING_POLITIC_CARDS_NUMBER))));
		}
	}
	
}
