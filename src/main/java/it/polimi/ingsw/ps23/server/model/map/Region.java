package it.polimi.ingsw.ps23.server.model.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.regions.City;

public abstract class Region {
	
	private String name;
	private Map<String, City> cities;
	private Bonus bonusTile;
	private List<City> citiesList;
	private boolean alreadyAcquiredBonusTile;
	
	public Region(String name, Bonus bonusTile) {
		this.name = name;
		this.bonusTile = bonusTile;
		cities = new HashMap<>();
		citiesList = new ArrayList<>();
		alreadyAcquiredBonusTile = false;
	}
	
	public void toCitiesList() {
		citiesList.addAll(cities.values());
	}
	
	protected List<City> getCitiesList() {
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
	
	protected Bonus getBonusTile() {
		return bonusTile;
	}
	
	public String getName() {
		return name;
	}

	protected boolean alreadyUsedBonusTile() {
		return alreadyAcquiredBonusTile;
	}

}
