package it.polimi.ingsw.ps23.server.model.map;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.player.BuiltEmporiumsSet;
//TODO return null
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
		for(Region region : groupRegionalCities) {
			if(region.getName().equals(regionName)) {
				selectedRegion = region;
			}
		}
		return selectedRegion;	
	}

	public String getColoredBonusTileString() {
		StringBuilder loopPrint = new StringBuilder();
		for(Region region : groupColoredCities) {
			loopPrint.append("\n\t- " + region.getName() + ": " + region.getBonusTile());
			if(region.alreadyUsedBonusTile()) {
				loopPrint.append(" (Already acquired)");
			}
		}
		return new String() + loopPrint;
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

	//TODO BUG contains
	private boolean isFoundRegion(Region region, BuiltEmporiumsSet builtEmporiumsSet) {
		return builtEmporiumsSet.getBuiltEmporiumSet().contains(region.getCitiesList()) && !(region.alreadyUsedBonusTile());
	}
	
	public Region groupRegionalCitiesComplete(BuiltEmporiumsSet builtEmporiumSet) {
		for(Region region : groupRegionalCities) {
			if(isFoundRegion(region, builtEmporiumSet)) {
				return region;
			}
		}
		return null;
	}

	public Region groupColoredCitiesComplete(BuiltEmporiumsSet builtEmporiumSet) {
		for(Region region : groupColoredCities) {
			if(isFoundRegion(region, builtEmporiumSet)) {
				return region;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		String print = "\t\t\t\t\t+++++++REGIONS+++++++\n\n";
		StringBuilder loopPrint = new StringBuilder();
		for(Region region : groupRegionalCities) {
			loopPrint.append(region.toString() + "\n");
		}
		return print + loopPrint;
	}

}
