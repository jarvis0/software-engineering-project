package it.polimi.ingsw.ps23.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Deck;

public class Game {
	
	private ArrayList<City> cities;
	private Deck politicDeck;
	private Deck permissionDeck;
	private final static String path = ("src/main/java/it/polimi/ingsw/ps23/csv/");
	private final static String citiesCVS = ("cities.csv");
	private final static String connectionsCVS = ("citiesConnections.csv");
	private final static String councillorsCVS = ("councillors.csv");
	private final static String permissionDeckCVS = ("permissionDeck.csv");
	private final static String politicDeckCVS = ("politicDeck.csv");
	
	public Game() {
		loadCities();
		loadPoliticDeck();
		loadPermissionDeck();
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
			rawCities = parseCSVFile( path + citiesCVS);
		} catch (IOException e) {
			System.out.println("Cannot load cities.");
		}
		cities = new CitiesFactory().makeCities(rawCities);
		System.out.println(cities);
	}
	
	private void loadPoliticDeck() {
		List<String[]> rawPoliticCards = new ArrayList<>();
		try {
			rawPoliticCards = parseCSVFile( path + politicDeckCVS);
		} catch (IOException e) {
			System.out.println("Cannot load politic deck.");
		}
		politicDeck = new PoliticDeckFactory().makeDeck(rawPoliticCards);
		System.out.println(politicDeck.toString());
	}
	
	private void loadPermissionDeck() {
		List<String[]> rawPermissionCards = new ArrayList<>();
		try {
			rawPermissionCards = parseCSVFile( path + permissionDeckCVS);
		} catch (IOException e) {
			System.out.println("Cannot load permission deck.");
		}
		permissionDeck = new PermissionDeckFactory().makeDeck(rawPermissionCards);
		System.out.println(permissionDeck);
	}
	
	private void loadCouncillors() {
		List<String[]> rawCouncillors = new ArrayList<>();
		try{
			rawCouncillors = parseCSVFile(path + councillorsCVS);		
		} catch(IOException e) {
			System.out.println("Cannot load permission deck.");
		}
		
		 
		
	}
}
