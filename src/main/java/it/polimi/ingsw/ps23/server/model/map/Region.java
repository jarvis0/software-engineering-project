package it.polimi.ingsw.ps23.server.model.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.regions.City;

/**
 * Superclass for both groupRegionalCities and groupColoredCities.
 * Provides useful and shared methods used by subclasses.
 * @author Alessandro Erba
 *
 */
public abstract class Region implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6779412923659189756L;
	private String name;
	private Map<String, City> cities;
	private Bonus bonusTile;
	private List<City> citiesList;
	private boolean alreadyAcquiredBonusTile;
	
	/**
	 * Initializes some class attributes. More attributes will be
	 * initialized by subclasses.
	 * @param name - name of the region
	 * @param bonusTile - bonus tile associate to the specified region parameter
	 */
	public Region(String name, Bonus bonusTile) {
		this.name = name;
		this.bonusTile = bonusTile;
		cities = new HashMap<>();
		citiesList = new ArrayList<>();
		alreadyAcquiredBonusTile = false;
	}
	
	/**
	 * Converts game cities from HashMap to List for further
	 * better code implementations.
	 */
	public void toCitiesList() {
		citiesList.addAll(cities.values());
	}
	
	protected List<City> getCitiesList() {
		return citiesList;
	}

	protected Map<String, City> getCities() {
		return cities;
	}
	
	/**
	 * Adds a game city to the cities HashMap.
	 * @param city - game city to be added to the related
	 * data structure
	 * @throws InvalidCityException if the specified city has been already
	 * inserted
	 */
	public void addCity(City city) throws InvalidCityException {
		String cityName = city.getName();
		if(!cities.containsKey(cityName)) {
			cities.put(cityName, city);
		}
		else
			throw new InvalidCityException();
	}
	
	/**
	 * Set the bonus tile of the specified region or colored cities group
	 * as acquired so no one else can acquire this bonus tile.
	 * @return the acquired regional or colored cities group bonus tile.
	 */
	public Bonus acquireBonusTile() {
		alreadyAcquiredBonusTile = true;
		return bonusTile;
	}
	
	/**
	 * get the bonus tile of the specified region or colored cities group
	 * @return the bonus tile of the specified region
	 */
	public Bonus getBonusTile() {
		return bonusTile;
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * check if the bonus tile of a specified region is already acquired or not
	 * @return the boolean variable that indicates if the bonus tile is acquired or not
	 */
	public boolean alreadyUsedBonusTile() {
		return alreadyAcquiredBonusTile;
	}

}
