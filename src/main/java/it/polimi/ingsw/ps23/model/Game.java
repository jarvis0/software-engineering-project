package it.polimi.ingsw.ps23.model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import it.polimi.ingsw.ps23.model.map.CapitalCity;
import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.NormalCity;
import it.polimi.ingsw.ps23.model.map.PoliticCard;
import it.polimi.ingsw.ps23.model.map.PoliticDeck;

public class Game {
	
	private final String capital = "Capital";
	private ArrayList<City> cities;
	private Deck politicDeck;
	
	public Game() {
		cities = loadCities();
		politicDeck = loadPoliticDeck();
	}
	
	private List<String[]> parseCSVFile(String path) throws IOException {
		CSVReader reader = new CSVReader(new FileReader(path));
		List<String[]> read = reader.readAll();
		reader.close();
		return read;
	}

	private ArrayList<City> loadCities() {
		List<String[]> rawCities = new ArrayList<>();
		ArrayList<City> citiesGoal = new ArrayList<>();
		try {
			rawCities = parseCSVFile("src/main/java/it/polimi/ingsw/ps23/CSV/cities.csv");
		} catch (IOException e) {
			System.err.println("Cannot load cities.");
		}
		for(String[] rawCity : rawCities) {
			if(!rawCity[3].equals(capital)) {
				citiesGoal.add(new NormalCity(rawCity[0], GameColorFactory.makeColor(rawCity[2], rawCity[1])));
			}
			else {
				citiesGoal.add(new CapitalCity(rawCity[0], GameColorFactory.makeColor(rawCity[2], rawCity[1])));
			}
		}
		System.out.println(citiesGoal);
		return citiesGoal;
	}
	
	private PoliticDeck loadPoliticDeck() {
		List<String[]> rawPoliticCards = new ArrayList<>();
		ArrayList<Card> politicCards = new ArrayList<>();
		try {
			rawPoliticCards = parseCSVFile("src/main/java/it/polimi/ingsw/ps23/CSV/politicDeck.csv");
		} catch (IOException e) {
			System.out.println("Cannot load politic deck.");
		}
		for(String[] rawPoliticCard : rawPoliticCards) {
			int sameColorPoliticNumber = Integer.parseInt(rawPoliticCard[0]);
			for(int i = 0; i < sameColorPoliticNumber; i++) {
				politicCards.add(new PoliticCard(GameColorFactory.makeColor(rawPoliticCard[2], rawPoliticCard[1])));
			}
		}
		System.out.println(politicCards);	
		
		PoliticDeck deck = new PoliticDeck(politicCards);
		System.out.println(deck.toString());
		return deck;
	}
	
}
