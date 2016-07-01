package it.polimi.ingsw.ps23.server.model.initialization;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Random;

import it.polimi.ingsw.ps23.server.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.server.model.map.CitiesGraph;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.GameMap;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.board.FreeCouncillorsSet;
import it.polimi.ingsw.ps23.server.model.map.board.King;
import it.polimi.ingsw.ps23.server.model.map.board.KingRewardTilesSet;
import it.polimi.ingsw.ps23.server.model.map.board.NobilityTrack;
import it.polimi.ingsw.ps23.server.model.map.regions.CapitalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PlayersSet;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;

/**
 * Initializes game resources from configuration files.
 * @author Giuseppe Mascellaro & Mirco Manzoni & Alessandro Erba
 *
 */
public class Initialization {
	
	private static final String CONFIGURATION_PATH = "src/main/java/it/polimi/ingsw/ps23/server/model/initialization/configuration/";
	private static final String MAPS_CSV = "maps.csv";
	private static final String CITIES_CSV = "cities.csv";
	private static final String CONNECTIONS_CSV = "citiesConnections.csv";
	private static final String COUNCILLORS_CSV = "councillors.csv";
	private static final String PERMISSION_DECK_CSV = "permissionDecks.csv";
	private static final String POLITIC_DECK_CSV = "politicDeck.csv";
	private static final String REWARD_TOKENS_CSV = "rewardTokens.csv";
	private static final String REGIONS_CSV = "regions.csv";
	private static final String GROUP_COLORED_CSV = "groupColoredCitiesBonusTiles.csv";
	private static final String KING_BONUS_TILE_CSV = "kingBonusTiles.csv";
	private static final String NOBILY_TRACK_CSV = "nobilityTrack.csv";
	
	private static final int STARTING_COINS = 100;//TODO rimettere 10
	private static final int STARTING_ASSISTANTS = 10;//TODO rimettere 1
	private static final int STARTING_POLITIC_CARDS_NUMBER = 10;//TODO rimettere 6
	
	private String mapPath;
	private String chosenMap;
	private Deck politicDeck;
	private FreeCouncillorsSet freeCouncillors;
	private BonusCache bonusCache;
	private GameMap gameMap;
	private King king;
	private KingRewardTilesSet kingTiles;
	private NobilityTrack nobilityTrack;
	private PlayersSet playerSet;
	
	/**
	 * Initializes game resources in order to provide a new game setup
	 * from given map choice and in game players.
	 * @param playersName - players name to be part of the game
	 */
	public Initialization(List<String> playersName) {
		chooseMap();
		loadPoliticDeck();
		loadCouncillors();
		bonusCache = new BonusCache();
		loadMap();
		createKing();
		loadKingTiles();
		loadNobilityTrack();
		loadPlayers(playersName);
	}
	
	public String getChosenMap() {
		return chosenMap;
	}
	
	public Deck getPoliticDeck() {
		return politicDeck;
	}

	public FreeCouncillorsSet getFreeCouncillors() {
		return freeCouncillors;
	}

	public GameMap getGameMap() {
		return gameMap;
	}

	public King getKing() {
		return king;
	}

	public KingRewardTilesSet getKingTiles() {
		return kingTiles;
	}

	public NobilityTrack getNobilityTrack() {
		return nobilityTrack;
	}

	public PlayersSet getPlayersSet() {
		return playerSet;
	}
	
	private void chooseMap() {
		List<String[]> rawMaps = new RawObject(CONFIGURATION_PATH + MAPS_CSV).getRawObject();
		chosenMap = rawMaps.get(new Random().nextInt(rawMaps.size()))[0];
		mapPath = CONFIGURATION_PATH + chosenMap;
		chosenMap = chosenMap.substring(0, chosenMap.indexOf('/'));
	}
	
	private void loadPoliticDeck() {
		List<String[]> rawPoliticCards = new RawObject(mapPath + POLITIC_DECK_CSV).getRawObject();
		politicDeck = new PoliticCardsBuilder().makeDeck(rawPoliticCards);	
	}
	
	private void loadCouncillors() {
		List<String[]> rawCouncillors = new RawObject(mapPath + COUNCILLORS_CSV).getRawObject();
		freeCouncillors = new CouncillorsBuilder().makeCouncillors(rawCouncillors);
	}
	
	private CitiesBuilder loadCities() {
		List<String[]> rawCities = new RawObject(mapPath + CITIES_CSV).getRawObject();
		List<String[]> rawRewardTokens = new RawObject(mapPath + REWARD_TOKENS_CSV).getRawObject();
		CitiesBuilder citiesFactory = new CitiesBuilder();
		citiesFactory.makeCities(rawCities, rawRewardTokens, bonusCache);
		return citiesFactory;
	}
	
	private CitiesGraphBuilder loadCitiesConnections(Map<String, City> cities) {
		List<String[]> rawCitiesConnections = new RawObject(mapPath + CONNECTIONS_CSV).getRawObject();
		CitiesGraphBuilder citiesGraphFactory = new CitiesGraphBuilder();
		citiesGraphFactory.makeCitiesGraph(rawCitiesConnections, cities);
		return citiesGraphFactory;
	}

	private List<Region> loadRegions(Map<String, City> citiesMap, Map<String, List<String>> citiesConnections) {
		List<String[]> rawRegions = new RawObject(mapPath + REGIONS_CSV).getRawObject();
		return new GroupRegionalCitiesBuilder().makeRegions(rawRegions, citiesMap, citiesConnections);
	}
	
	private void regionalCouncils(List<Region> regions) {
		for(Region region : regions) {
			((GroupRegionalCity) region).setCouncil(new CouncilBuilder().makeCouncil(freeCouncillors));
		}
	}

	private Map<String, Deck> loadPermissionDecks(Map<String, City> cities) {
		List<String[]> rawPermissionCards = new RawObject(mapPath + PERMISSION_DECK_CSV).getRawObject();
		return new PermitTilesBuilder(rawPermissionCards, cities).makeDecks(bonusCache);
	}
	
	private void regionalPermissionDecks(Map<String, City> cities, List<Region> regions) {
		Map<String, Deck> permissionDeck = loadPermissionDecks(cities);
		for(Region region : regions) {
			((GroupRegionalCity) region).setPermitTiles(permissionDeck.get(region.getName()));
		}
	}

	private List<Region> loadColoredRegions(List<City> cities) {
		List<String[]> rawColoredCities = new RawObject(mapPath + GROUP_COLORED_CSV).getRawObject();
		return new GroupColoredCitiesBuilder().makeGroup(rawColoredCities, cities);
	}

	private void loadMap() {
		CitiesBuilder citiesFactory = loadCities();
		List<City> citiesList = citiesFactory.getCities();
		Map<String, City> citiesMap = citiesFactory.getHashMap();
		CitiesGraphBuilder citiesGraphFactory = loadCitiesConnections(citiesMap);
		CitiesGraph citiesGraph = citiesGraphFactory.getCitiesGraph();
		Map<String, List<String>> citiesConnections = citiesGraphFactory.getCitiesConnections();
		List<Region> groupRegionalCities = loadRegions(citiesMap, citiesConnections);
		regionalCouncils(groupRegionalCities);
		regionalPermissionDecks(citiesMap, groupRegionalCities);
		List<Region> groupColoredCities = loadColoredRegions(citiesList);
		gameMap = new GameMap(citiesMap, citiesGraph, groupRegionalCities, groupColoredCities);
	}
	
	private void createKing() {
		Map<String, City> cities = gameMap.getCities();		
		Set<Entry<String, City>> citiesMapEntrySet = cities.entrySet();
		for(Entry<String, City> city : citiesMapEntrySet) {
			City currentCity = city.getValue();
			if(currentCity instanceof CapitalCity) {  
				king = new King(currentCity, new CouncilBuilder().makeCouncil(freeCouncillors));
				return;
			}
		}
	}
	
	private void loadKingTiles() {
		List<String[]> rawKingTiles = new RawObject(mapPath + KING_BONUS_TILE_CSV).getRawObject();
		kingTiles = new KingTilesBuilder().makeTiles(rawKingTiles);
	}
	
	private void loadNobilityTrack() {
		List<String[]> rawNobilityTrackSteps = new RawObject(mapPath + NOBILY_TRACK_CSV).getRawObject();
		nobilityTrack = new NobilityTrackBuilder().makeNobilityTrack(rawNobilityTrackSteps, bonusCache);
	}

	private void loadPlayers(List<String> playersName) {
		playerSet = new PlayersSet();
		int playersNumber = playersName.size();
		for(int i = 0; i < playersNumber; i++) {
			Player player = new Player(playersName.get(i), STARTING_COINS + i, STARTING_ASSISTANTS + i, new PoliticHandDeck(politicDeck.pickCards(STARTING_POLITIC_CARDS_NUMBER)));
			playerSet.addPlayer(player);
		}
	}
	
}
