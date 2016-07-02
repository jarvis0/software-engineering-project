package it.polimi.ingsw.ps23.server.model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;

public class BuiltEmporiumsSet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6249094998409712661L;

	private static final int MAX_EMPORIUMS = 2;//TODO reset to real game default value
	
	private List<City> builtEmporiums;
	
	BuiltEmporiumsSet() {
		builtEmporiums = new ArrayList<>();
	}
	
	void addBuiltEmporium(City city) {
		builtEmporiums.add(city);
	}
	
	boolean containsMaxEmporium() {
		return builtEmporiums.size() == MAX_EMPORIUMS;
	}

	public List<City> getBuiltEmporiumsSet() {
		return builtEmporiums;
	}

	BuiltEmporiumsSet getCitiesForRecycleRewardTokens() {
		BuiltEmporiumsSet citiesWithoutNobilityTrackPoints = new BuiltEmporiumsSet();
		for(City city : builtEmporiums) {
			if(city instanceof NormalCity && !((NormalCity)city).hasNobilityTrackBonus()) {
				citiesWithoutNobilityTrackPoints.builtEmporiums.add(city);
			}
		}
		return citiesWithoutNobilityTrackPoints;				
	}

	public NormalCity getChosenCity(String cityName) throws InvalidCityException {
		for(City city : builtEmporiums) {
			if(city.getName().equals(cityName)) {
				return (NormalCity) city;
			}	
		}
		throw new InvalidCityException();
	}

	public String getCitiesPrint() {
		StringBuilder loopString = new StringBuilder();
		City city;
		if(!builtEmporiums.isEmpty()) {
			city = builtEmporiums.get(0);
			loopString.append(city.getName() + " " + city.getColor());
		}
		for(int i = 1; i < builtEmporiums.size(); i++) {
			city = builtEmporiums.get(i);
			loopString.append(", " + city.getName() + " " + city.getColor());
		}
		return "[" + loopString + "]";
	}

}
