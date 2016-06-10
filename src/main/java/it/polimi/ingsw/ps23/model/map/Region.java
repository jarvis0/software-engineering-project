package it.polimi.ingsw.ps23.model.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

public abstract class Region {
	
	private String name;
	private Map<String, City> cities;
	private Bonus bonusTile;
	private Collection<City> citiesList;
	private boolean alreadyAcquiredBonusTile;
	
	public Region(String name, Bonus bonusTile) {
		cities = new HashMap<>();
		this.name = name;
		this.bonusTile = bonusTile;
		citiesList = toList();
		alreadyAcquiredBonusTile = false;
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
	
	public Bonus getBonusTile() {
			alreadyAcquiredBonusTile = true;
			return bonusTile;
	}
	
	public String getName() {
		return name;
	}
	
	private Collection<City> toList() {
		return cities.values();
	}
	
	@Override
	public String toString() {
		String print = "\n> " + name + ":\n\n";
		print += "\t- CITIES:\n";
		for(City city : citiesList) {
			print += "\t\t» " + city.toString();
		}
		print += "\n\t- REGIONAL BONUS TILE: " + bonusTile;
		if(alreadyAcquiredBonusTile) {
			print += " (Already acquired)";
		}
		return print;
	}

	public boolean containsAll(List<City> builtEmporiumSet) {
		return citiesList.containsAll(builtEmporiumSet);
	}

	public boolean alreadyUsedBonusTile() {
		return alreadyAcquiredBonusTile;
	}

}
