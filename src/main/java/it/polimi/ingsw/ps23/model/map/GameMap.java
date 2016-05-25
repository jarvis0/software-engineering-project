package it.polimi.ingsw.ps23.model.map;

import java.util.Map;
import java.util.List;

import it.polimi.ingsw.ps23.model.CitiesGraph;

public class GameMap {
	
	private List<City> citiesList;
	private Map<String, City> citiesMap;
	private CitiesGraph citiesGraph;	
	private List<Region> groupRegionalCities;
	private List<Region> groupColoredCities;
	
	public GameMap(List<City> citiesList, Map<String, City> citiesMap, CitiesGraph citiesGraph, List<Region> groupRegionalCities, List<Region> groupColoredCities) {
		this.citiesList = citiesList;
		this.citiesMap = citiesMap;
		this.citiesGraph = citiesGraph;
		this.groupRegionalCities = groupRegionalCities;
		this.groupColoredCities = groupColoredCities;
	}

	public CitiesGraph getCitiesGraph() {
		return citiesGraph;
	}

	public Map<String, City> getCitiesMap() {
		return citiesMap;
	}

	public List<City> getCitiesList() {
		return citiesList;
	}

	public List<Region> getGroupRegionalCity() {
		return groupRegionalCities;
	}

	public List<Region> getGroupColoredCity() {
		return groupColoredCities;
	}
	
	@Override
	public String toString() {
		//return groupRegionalCities.toString() + "\n" + groupColoredCities.toString();
		return groupRegionalCities.toString();
	}

}
