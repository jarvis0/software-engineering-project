package it.polimi.ingsw.ps23.model;

import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Council;
import it.polimi.ingsw.ps23.model.map.CouncilFactory;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.FreeCouncillors;


public class Game {
	
	private HashMap<String, City> cities; //?
	private Deck politicDeck;
	private Deck permissionDeck;
	private FreeCouncillors freeCouncillors;
	private Council seasideCouncil;
	private Council hillCouncil;
	private Council mountainCouncil;

	private static final String PATH = "src/main/java/it/polimi/ingsw/ps23/csv/";
	private static final String CITIES_CSV = "cities.csv";
	private static final String CONNECTIONS_CSV = "citiesConnections.csv";
	private static final String COUNCILLORS_CSV = "councillors.csv";
	private static final String PERMISSION_DECK_CSV = "permissionDeck.csv";
	private static final String POLITIC_DECK_CSV = "politicDeck.csv";
	private static final String REWARD_TOKENS_CSV = "rewardTokens.csv";
	
	public Game() {
		loadCities();
		loadConnection();
		loadPoliticDeck();
		loadPermissionDeck();
		loadCouncillors();
	}

	private void loadConnection() {
						
	}
	
	private void loadCities() {
		List<String[]> rawCities = new RawObject(PATH + CITIES_CSV).getRawObject();
		List<String[]> rawRewardTokens = new RawObject(PATH + REWARD_TOKENS_CSV).getRawObject();
		cities = (HashMap<String, City>) new CitiesFactory().makeCities(rawCities, rawRewardTokens);
		System.out.println(cities);
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
