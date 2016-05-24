package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.FreeCouncillors;
import it.polimi.ingsw.ps23.model.map.GroupRegionalCity;
import it.polimi.ingsw.ps23.model.map.Region;


public class Game {
	
	private CitiesGraph citiesGraph;
	private ArrayList<GroupRegionalCity> regions;
	private Deck politicDeck;
	private Deck permissionDeck;
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
	
	public Game() {
		loadMap();
		loadPoliticDeck();
		loadPermissionDeck();
		loadCouncillors();
		connectCouncillorsToGroupRegionalCity();
		connectPermissionCardToGroupRegionalCity();
	}
	
	private void loadMap() {
		List<String[]> rawCities = new RawObject(PATH + CITIES_CSV).getRawObject();
		List<String[]> rawRewardTokens = new RawObject(PATH + REWARD_TOKENS_CSV).getRawObject();
		List<String[]> rawCitiesConnections = new RawObject(PATH + CONNECTIONS_CSV).getRawObject();
		List<String[]> rawRegion = new RawObject(PATH + REGIONS).getRawObject();
		CitiesFactory citiesFactory = new CitiesFactory();
		List<City> cities = (ArrayList<City>) citiesFactory.makeCities(rawCities, rawRewardTokens);
		
		List<String[]> rawColouredCities = new RawObject(PATH + GROUP_COLORED_CSV).getRawObject();
		ArrayList<Region> colouredGroup  = (ArrayList<Region>) new GroupColoredCityFactory().makeGroup(rawColouredCities, cities);
		System.out.println(colouredGroup);
		
		HashMap<String, City> citiesMap = (HashMap<String, City>) citiesFactory.toHashMap();
		System.out.println(citiesMap);
		
		citiesGraph = new CitiesGraph(rawCitiesConnections, citiesMap);
		regions = new GroupRegionalCityFactory().makeRegions(rawRegion, citiesMap);
		System.out.println(citiesGraph);		

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
		System.out.println("\n" + freeCouncillors);
	}

	private void connectCouncillorsToGroupRegionalCity() {
		for (GroupRegionalCity groupRegionalCity : regions) {
			groupRegionalCity.setCouncil(new CouncilFactory().makeCouncil(freeCouncillors));
		}
		System.out.println("\n" + regions);
	}
	
	private void connectPermissionCardToGroupRegionalCity() {
		for (GroupRegionalCity groupRegionalCity : regions) {
			//aggiungere le permission card per ogni regione
		}
	}
	
}
