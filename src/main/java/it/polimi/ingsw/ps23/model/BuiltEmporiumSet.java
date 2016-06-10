package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.NormalCity;

public class BuiltEmporiumSet {
	
	private static final int MAX_EMPORIUMS = 10;
	
	private List<City> builtEmporiums;
	
	public BuiltEmporiumSet() {
		builtEmporiums = new ArrayList<>();
		
	}
	
	public void addBuiltEmporium(City city) throws InvalidPositionException {
		if(!builtEmporiums.contains(city)) {
			builtEmporiums.add(city);
		}
		else {
			throw new InvalidPositionException();
		}
	}
	
	public boolean containsTenEmporium() {
		return builtEmporiums.size() == MAX_EMPORIUMS;
	}

	public List<City> getBuiltEmporiumSet() {
		return builtEmporiums;
	}

	@Override
	public String toString() {
		return builtEmporiums.toString();
	}

	public BuiltEmporiumSet getCitiesForRecycleRewardTokens() {
		BuiltEmporiumSet citiesWithoutNobilityTrackPoints = new BuiltEmporiumSet();
		for (City city : builtEmporiums) {
			if(city instanceof NormalCity && !((NormalCity)city).hasNobilityTrackPoints()) {
					citiesWithoutNobilityTrackPoints.builtEmporiums.add(city);
			}
		}
		return citiesWithoutNobilityTrackPoints;				
	}

	public NormalCity get(String cityName) {
		for(City city : builtEmporiums) {
			if (city.getName().equals(cityName)) {
				return (NormalCity) city;
			}	
		}
		return null;
		//TODO remove return null
	}
	
}
