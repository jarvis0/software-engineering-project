package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.City;

public class BuiltEmporiumSet {
	
	private static final int MAX_EMPORIUMS = 10;
	
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
	
	public boolean containsTenEmporium() {
		return builtEmporiumSet.size() == MAX_EMPORIUMS;
	}
	
	
	
	public List<City> getBuiltEmporiumSet() {
		return builtEmporiumSet;
	}

	@Override
	public String toString() {
		return builtEmporiumSet.toString();
	}

}
