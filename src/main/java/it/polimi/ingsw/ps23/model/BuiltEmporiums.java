package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.City;

public class BuiltEmporiums {
	
	private List<City> builtEmporiums;
	
	public BuiltEmporiums() {
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

}
