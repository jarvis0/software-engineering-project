package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.FreeCouncillors;

import it.polimi.ingsw.ps23.model.map.Region;


public class Game {
	
	private CitiesGraph citiesGraph;
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
		
	}
	
	private void loadMap() {
		List<String[]> rawCities = new RawObject(PATH + CITIES_CSV).getRawObject();
		List<String[]> rawRewardTokens = new RawObject(PATH + REWARD_TOKENS_CSV).getRawObject();
		List<String[]> rawCitiesConnections = new RawObject(PATH + CONNECTIONS_CSV).getRawObject();
		List<String[]> rawRegion = new RawObject(PATH + REGIONS).getRawObject();
		ArrayList<City> cities = (ArrayList<City>) new CitiesFactory().makeCities(rawCities, rawRewardTokens);
		//citiesGraph = new CitiesGraph(rawCitiesConnections, cities);
		//System.out.println(citiesGraph);
		List<String[]> rawColouredCities = new RawObject(PATH + GROUP_COLORED_CSV).getRawObject();
		ArrayList<Region> colouredGroup  = new GroupColoredCityFactory().makeGroup(rawColouredCities,cities);
		System.out.println(colouredGroup);
	}

	private void loadPoliticDeck() {
		List<String[]> rawPoliticCards = new RawObject(PATH + POLITIC_DECK_CSV).getRawObject();
		politicDeck = new PoliticDeckFactory().makeDeck(rawPoliticCards);
		System.out.println(politicDeck);
	}
	
	private void loadPermissionDeck() {
		List<String[]> rawPermissionCards = new RawObject(PATH + PERMISSION_DECK_CSV).getRawObject();
		permissionDeck = new PermissionDeckFactory().makeDeck(rawPermissionCards);
		System.out.println(permissionDeck);
	}
	
	private void loadCouncillors() {
		List<String[]> rawCouncillors = new RawObject(PATH + COUNCILLORS_CSV).getRawObject();
		freeCouncillors = new CouncillorsFactory().makeCouncillors(rawCouncillors);
		System.out.println(freeCouncillors);
	}
	
	
	//the following method will be called by regions
	/*private void createCouncils() {
		seasideCouncil = new CouncilFactory().makeCouncil(freeCouncillors);
		hillCouncil = new CouncilFactory().makeCouncil(freeCouncillors);
		mountainCouncil = new CouncilFactory().makeCouncil(freeCouncillors);
		
		System.out.println("sea " +seasideCouncil);
		System.out.println("hill " +hillCouncil);
		System.out.println("mountain " +mountainCouncil);
		System.out.println(freeCouncillors);
		freeCouncillors.selectCouncillor(2, seasideCouncil); //test change council
		System.out.println("sea " +seasideCouncil);
		System.out.println(freeCouncillors);
	}
	*/
	
}
