package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class GroupColoredCity extends Region {

	private ArrayList<String> cities;
	

	public GroupColoredCity(String id, ArrayList<String> cities, Bonus victoryPointsBonus){
		super(id, victoryPointsBonus);
		this.cities = cities;
	}
	
	@Override
	public String toString() {
		return getId() + ": " + cities + " [Bonus: " + getBonus() + "]"; 
	}
	
	
}

