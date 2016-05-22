package it.polimi.ingsw.ps23.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.opencsv.CSVReader;

import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Deck;

public class Game {
	
	private HashMap<String, City> cities;
	private Deck politicDeck;
	private Deck permissionDeck;
	private static final String PATH = "src/main/java/it/polimi/ingsw/ps23/csv/";
	private static final String CITIES_CSV = "cities.csv";
	private static final String CONNECTIONS_CSV = "citiesConnections.csv";
	private static final String COUNCILLORS_CSV = "councillors.csv";
	private static final String PERMISSION_DECK_CSV = "permissionDeck.csv";
	private static final String POLITIC_DECK_CSV = "politicDeck.csv";
	
	public Game() {
		loadCities();
		loadConnection();
		loadPoliticDeck();
		loadPermissionDeck();
	}

	private void loadConnection() {
						
	}

	private List<String[]> parseCSVFile(String path) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path));
		List<String[]> read = reader.readAll();
		reader.close();
		return read;
	}

	private void loadCities() {
		List<String[]> rawCities = new ArrayList<>();
		try {
			rawCities = parseCSVFile(PATH + CITIES_CSV);
		} catch (IOException e) {
			System.out.println("Cannot load cities.");
		}
		cities = new CitiesFactory().makeCities(rawCities);
		System.out.println(cities);
	}
	
	private void loadPoliticDeck() {
		List<String[]> rawPoliticCards = new ArrayList<>();
		try {
			rawPoliticCards = parseCSVFile(PATH + POLITIC_DECK_CSV);
		} catch (IOException e) {
			System.out.println("Cannot load politic deck.");
		}
		politicDeck = new PoliticDeckFactory().makeDeck(rawPoliticCards);
		System.out.println(politicDeck.toString());
	}
	
	private void loadPermissionDeck() {
		List<String[]> rawPermissionCards = new ArrayList<>();
		try {
			rawPermissionCards = parseCSVFile(PATH + PERMISSION_DECK_CSV);
		} catch (IOException e) {
			System.out.println("Cannot load permission deck.");
		}
		permissionDeck = new PermissionDeckFactory().makeDeck(rawPermissionCards);
		System.out.println(permissionDeck);
	}
	
	private void loadCouncillors() {
		List<String[]> rawCouncillors = new ArrayList<>();
		try{
			rawCouncillors = parseCSVFile(PATH + COUNCILLORS_CSV);		
		} catch(IOException e) {
			System.out.println("Cannot load permission deck.");
		}
		
		 
		
	}
}
