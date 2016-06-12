package it.polimi.ingsw.ps23.model.player;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.InvalidPositionException;
import it.polimi.ingsw.ps23.model.map.regions.City;
import it.polimi.ingsw.ps23.model.map.regions.NormalCity;

public class BuiltEmporiumSet {
	
	private static final int MAX_EMPORIUMS = 2;
	
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
	
	public boolean containsMaxEmporium() { //TODO max emporium
		return builtEmporiums.size() == MAX_EMPORIUMS;
	}

	public List<City> getBuiltEmporiumSet() {
		return builtEmporiums;
	}

	public BuiltEmporiumSet getCitiesForRecycleRewardTokens() {
		BuiltEmporiumSet citiesWithoutNobilityTrackPoints = new BuiltEmporiumSet();
		for(City city : builtEmporiums) {
			if(city instanceof NormalCity && !((NormalCity)city).hasNobilityTrackBonus()) {
				citiesWithoutNobilityTrackPoints.builtEmporiums.add(city);
			}
		}
		return citiesWithoutNobilityTrackPoints;				
	}

	public NormalCity get(String cityName) {
		for(City city : builtEmporiums) {
			if(city.getName().equals(cityName)) {
				return (NormalCity) city;
			}	
		}
		return null;
	}

	@Override
	public String toString() {
		return builtEmporiums.toString();
	}

	public String getCities() {
		StringBuilder loopString = new StringBuilder();
		City city = builtEmporiums.get(0);
		if(!builtEmporiums.isEmpty()) {
			loopString.append(city.getName() + " " + city.getColor());
		}
		for(int i = 1; i < builtEmporiums.size(); i++) {
			city = builtEmporiums.get(i);
			loopString.append(", " + city.getName() + " " + city.getColor());
		}
		return "[" + new String() + loopString + "]";
	}

}
