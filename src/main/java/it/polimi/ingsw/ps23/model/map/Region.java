package it.polimi.ingsw.ps23.model.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

public abstract class Region {
	
	private String name;
	private Map<String, City> cities;
	private Bonus victoryPointsBonus;
	
	public Region(String name, Bonus victoryPointBonus) {
		cities = new HashMap<>();
		this.name = name;
		this.victoryPointsBonus = victoryPointBonus;
	}
	
	protected Map<String, City> getCities() {
		return cities;
	}
	
	public void addCity(City city) throws InvalidCityException {
		String cityName = city.getName();
		if(!cities.containsKey(cityName)) {
			cities.put(cityName, city);
		}
		else
			throw new InvalidCityException();
	}
	
	protected Bonus getBonus() {
		return victoryPointsBonus;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		String print = "\n> " + name + ":\n\n";
		print += "\t- CITIES:\n";
		Collection<City> citiesValues = cities.values();
		for(City city : citiesValues) {
			print += "\t\t» " + city.toString();
		}
		print += "\n\t- REGIONAL BONUS TILE: " + victoryPointsBonus;
		return print;
	}

}
