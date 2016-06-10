package it.polimi.ingsw.ps23.model.map;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.ps23.model.CitiesGraph;

public class GameMap {
	
	private Map<String, City> citiesMap;
	private CitiesGraph citiesGraph;	
	private List<Region> groupRegionalCities;
	private List<Region> groupColoredCities;
	
	public GameMap(Map<String, City> citiesMap, CitiesGraph citiesGraph, List<Region> groupRegionalCities, List<Region> groupColoredCities) {
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
	

	public List<Region> getGroupRegionalCity() {
		return groupRegionalCities;
	}
	
	public Region getRegion(String regionName) {
		Region selectedRegion = null;
		for (Region region : groupRegionalCities) {
			if (region.getName().equals(regionName)) {
				selectedRegion = region;
			}
		}
		return selectedRegion;	
	}

	public List<Region> getGroupColoredCity() {
		return groupColoredCities;
	}
	
	public Map<String, Region> getRegionMap() {
		Map<String, Region> regionMap = new HashMap<>();
		for(Region region : getGroupRegionalCity()) {
			regionMap.put(region.getName(), (GroupRegionalCity) region);
		}
		return regionMap;
	}
	public Map<String, Deck> getPermitMap() {
	Map<String, Deck> permitsMap = new HashMap<>();
		for(Region region : getGroupRegionalCity()) {
			permitsMap.put(region.getName(), ((GroupRegionalCity) region).getPermissionDeckUp());
		}
		return permitsMap;
	}
	
	@Override
	public String toString() {
		String print = "\t\t\t\t\t+++++++REGIONS+++++++\n\n";
		for(Region region : groupRegionalCities) {
			print += region.toString() + "\n";
		}
		return print;
	}

}
