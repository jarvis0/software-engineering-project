package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.City;

public class BuiltEmporiumSet {
	
	private List<City> builtEmporiumSet;
	
	public BuiltEmporiumSet() {
		builtEmporiumSet = new ArrayList<>();
		
	}
	
	public void addBuiltEmporium(City city) throws InvalidPositionException {
		if(!builtEmporiumSet.contains(city)) {
			builtEmporiumSet.add(city);
		}
		else {
			throw new InvalidPositionException();
		}
	}

}
