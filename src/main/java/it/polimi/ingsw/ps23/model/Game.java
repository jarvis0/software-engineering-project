package it.polimi.ingsw.ps23.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.CSVReader;

import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Council;
import it.polimi.ingsw.ps23.model.map.CouncilFactory;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.FreeCouncillors;

public class Game {
	
	private ArrayList<City> cities;
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
	
	public Game() {
		loadCities();
		loadPoliticDeck();
		loadPermissionDeck();
		loadCouncillors();
		createCouncils();
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
			System.out.println("Cannot load councillors.");
		}
		freeCouncillors = new CouncillorsFactory().makeCouncillors(rawCouncillors);
		System.out.println(freeCouncillors);
	}
	
	private void createCouncils() {
		seasideCouncil = new CouncilFactory().makeCouncil(freeCouncillors);
		hillCouncil = new CouncilFactory().makeCouncil(freeCouncillors);
		mountainCouncil = new CouncilFactory().makeCouncil(freeCouncillors);
		
		System.out.println("sea " +seasideCouncil);
		System.out.println("hill " +hillCouncil);
		System.out.println("mountain " +mountainCouncil);
		System.out.println(freeCouncillors);
		freeCouncillors.selectCouncillor(2, seasideCouncil);
		System.out.println("sea " +seasideCouncil);
		System.out.println(freeCouncillors);
		}
	
}
