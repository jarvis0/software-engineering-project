package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.bonus.VictoryPointBonus;

public abstract class Region {
	
	private String id;
	private ArrayList<City> cities;
	VictoryPointBonus victoryPointsBonus;
	
	public Region(String id, VictoryPointBonus victoryPointBonus) {
		cities = new ArrayList<>();
		this.id = id;
		this.victoryPointsBonus = victoryPointBonus;
	}
	
	protected ArrayList<City> getCities() {
		return cities;
	}
	
	public void addCity(City city) throws InvalidCityException {
		if(!cities.contains(city)) {
		cities.add(city);
		}
		else
			throw new InvalidCityException();
	}
	
	public boolean canTakeBonus(Player player) {
		return player.getBuiltEmporium().contains(cities);//&& bonus già preso
	}
	
	public abstract void takeBonus(Player player);
	
	@Override
	public String toString() {
		return id + ": " + cities + " [Bonus: " + victoryPointsBonus + "]"; 
	}
}
