package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.bonus.RewardToken;
import it.polimi.ingsw.ps23.model.map.CapitalCity;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.NormalCity;

public class CitiesFactory {
	
	private static final String CAPITAL = "Capital";
	
	public ArrayList<City> makeCities(List<String[]> rawCities) {
		ArrayList<City> cities = new ArrayList<>();
		for(String[] rawCity : rawCities) {
			if(!rawCity[3].equals(CAPITAL)) {
				cities.add(new NormalCity(rawCity[0], GameColorFactory.makeColor(rawCity[2], rawCity[1]), new RewardToken()));
			}
			else {
				cities.add(new CapitalCity(rawCity[0], GameColorFactory.makeColor(rawCity[2], rawCity[1])));
			}
		}
		return cities;
	}
	
}
