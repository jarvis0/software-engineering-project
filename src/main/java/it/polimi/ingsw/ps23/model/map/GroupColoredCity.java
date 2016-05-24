package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class GroupColoredCity extends Region {

	private ArrayList<String> composition;
	
	public GroupColoredCity(String id, ArrayList<String> cities, Bonus victoryPointsBonus){
		super(id, victoryPointsBonus);
		composition = cities;
	}
	
	@Override
	public String toString() {
		return  getId() + " " + composition.toString() +" " + getBonus();
	}
	
	
}

