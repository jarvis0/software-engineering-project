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
			rawCities = parseCSVFile("src/main/java/it/polimi/ingsw/ps23/csv/cities.csv");
		} catch (IOException e) {
			System.out.println("Cannot load cities.");
		}
		cities = new CitiesFactory().makeCities(rawCities);
		System.out.println(cities);
	}
	
	private void loadPoliticDeck() {
		List<String[]> rawPoliticCards = new ArrayList<>();
		try {
			rawPoliticCards = parseCSVFile("src/main/java/it/polimi/ingsw/ps23/csv/politicDeck.csv");
		} catch (IOException e) {
			System.out.println("Cannot load politic deck.");
		}
		politicDeck = new PoliticDeckFactory().makeDeck(rawPoliticCards);
		System.out.println(politicDeck.toString());
	}
	
	private void loadPermissionDeck() {
		List<String[]> rawPermissionCards = new ArrayList<>();
		try {
			rawPermissionCards = parseCSVFile("src/main/java/it/polimi/ingsw/ps23/csv/permissionDeck.csv");
		} catch (IOException e) {
			System.out.println("Cannot load permission deck.");
		}
		permissionDeck = new PermissionDeckFactory().makeDeck(rawPermissionCards);
		System.out.println(permissionDeck);
	}
	
}
