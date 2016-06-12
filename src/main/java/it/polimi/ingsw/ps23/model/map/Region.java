package it.polimi.ingsw.ps23.model.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.map.regions.City;

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
		citiesList = toList(); //TODO uso improprio
		alreadyAcquiredBonusTile = false;
	}
	
	public Collection<City> getCitiesList() {
		return citiesList;
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
	
	public Bonus acquireBonusTile() {
		alreadyAcquiredBonusTile = true;
		return bonusTile;
	}
	
	public Bonus getBonusTile() {
		return bonusTile;
	}
	
	public String getName() {
		return name;
	}
	
	private Collection<City> toList() {
		return cities.values();
	}

	public boolean containsAll(List<City> builtEmporiumSet) {
		return citiesList.containsAll(builtEmporiumSet);
	}

	public boolean alreadyUsedBonusTile() {
		return alreadyAcquiredBonusTile;
	}

}
