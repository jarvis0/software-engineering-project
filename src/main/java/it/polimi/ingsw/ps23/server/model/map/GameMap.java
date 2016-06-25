package it.polimi.ingsw.ps23.server.model.map;

import java.util.Map;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.player.BuiltEmporiumsSet;

/**
 * Provides a unified representation of the game map which contains
 * all cities in two different representations: the first is accessible via
 * the city name and the second is useful for graph minimum walk.
 * <p>
 * Cities are organized into regional cities and same color groups in
 * order to differentiate the bonuses permit tile.
 * @author Alessandro Erba & Mirco Manzoni
 *
 */
public class GameMap implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8917662475849081509L;
	private Map<String, City> citiesMap;
	private transient CitiesGraph citiesGraph;
	private List<Region> groupRegionalCities;
	private List<Region> groupColoredCities;
	
	/**
	 * Constructs the whole game map which contains
	 * all cities in two different representations: the first is accessible via
	 * the city name and the second is useful for graph minimum walk.
	 * <p>
	 * Cities are organized into regional cities and same color cities in
	 * order to differentiate the bonuses permit tile.
	 * @param citiesMap - directly accessed cities via their names
	 * @param citiesGraph - graph cities representation for explore cities connections
	 * @param groupRegionalCities - cities organized into regions
	 * @param groupColoredCities - cities organized into same color groups
	 */
	public GameMap(Map<String, City> citiesMap, CitiesGraph citiesGraph, List<Region> groupRegionalCities, List<Region> groupColoredCities) {
		this.citiesMap = citiesMap;
		this.citiesGraph = citiesGraph;
		this.groupRegionalCities = groupRegionalCities;
		this.groupColoredCities = groupColoredCities;
	}

	public CitiesGraph getCitiesGraph() {
		return citiesGraph;
	}

	public Map<String, City> getCities() {
		return citiesMap;
	}	

	public List<Region> getGroupRegionalCity() {
		return groupRegionalCities;
	}
	
	/**
	 * Tries to find the region specified in the parameter.
	 * @param regionName - name of the region to be found
	 * @return region related to the specified regionName.
	 */
	public Region getRegion(String regionName) {
		for(Region region : groupRegionalCities) {
			if(region.getName().equals(regionName)) {
				return region;
			}
		}
		return null;
	}

	/**
	 * Provide a string representation of game groups colored cities, in particular the
	 * associated bonus permit tiles and a print whether they have been already
	 * acquired or not.
	 * @return a string representing the game groups colored cities.
	 */
	public String printColoredBonusTile() {
		StringBuilder loopPrint = new StringBuilder();
		for(Region region : groupColoredCities) {
			loopPrint.append("\n\t- " + region.getName() + ": " + region.getBonusTile());
			if(region.alreadyUsedBonusTile()) {
				loopPrint.append(" (Already acquired)");
			}
		}
		return new String() + loopPrint;
	}
	
	/**
	 * Maps each game region name with its effective region object.
	 * @return a HashMap containing region names and their effective object.
	 */
	public Map<String, Region> getRegionMap() {
		Map<String, Region> regionMap = new HashMap<>();
		for(Region region : getGroupRegionalCity()) {
			regionMap.put(region.getName(), (GroupRegionalCity) region);
		}
		return regionMap;
	}
	
	/**
	 * Maps each game region with its related permission cards up.
	 * @return a HashMap of game region names and their permission cards up.
	 */
	public Map<String, Deck> getPermissionCardsUp() {
		Map<String, Deck> permissionDecksUp = new HashMap<>();
		for(Region region : getGroupRegionalCity()) {
			permissionDecksUp.put(region.getName(), ((GroupRegionalCity) region).getPermissionDeckUp());
		}
		return permissionDecksUp;
	}

	private boolean isCompletedRegion(Region region, BuiltEmporiumsSet builtEmporiumsSet) {
		return builtEmporiumsSet.getBuiltEmporiumsSet().containsAll(region.getCitiesList()) && !(region.alreadyUsedBonusTile());
	}
	
	/**
	 * Checks if there is a region which a player has built at least
	 * an emporium in and if no one else have already reach this goal.
	 * @param builtEmporiumsSet - current player built emporiums
	 * @return the completed region if completed.
	 */
	public Region groupRegionalCitiesComplete(BuiltEmporiumsSet builtEmporiumsSet) {
		for(Region region : groupRegionalCities) {
			if(isCompletedRegion(region, builtEmporiumsSet)) {
				return region;
			}
		}
		return null;
	}
	
	/**
	 * Checks if there is a colored cities group which a player has built at least
	 * an emporium in and if no one else have already reach this goal.
	 * @param builtEmporiumsSet - current player built emporiums
	 * @return the completed colored cities group if completed
	 */
	public Region groupColoredCitiesComplete(BuiltEmporiumsSet builtEmporiumsSet) {
		for(Region region : groupColoredCities) {
			if(isCompletedRegion(region, builtEmporiumsSet)) {
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
			loopPrint.append(region + "\n");
		}
		return print + loopPrint;
	}

}
