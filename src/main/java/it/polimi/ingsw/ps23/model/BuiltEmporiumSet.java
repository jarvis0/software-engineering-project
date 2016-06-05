package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.City;

public class BuiltEmporiumSet {
	
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
	
	
	
	public List<City> getBuiltEmporiumSet() {
		return builtEmporiums;
	}

	@Override
	public String toString() {
		return builtEmporiums.toString();
	}

}
