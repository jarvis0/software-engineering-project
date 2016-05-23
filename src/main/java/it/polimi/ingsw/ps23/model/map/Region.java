package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.Player;

public abstract class Region {
	
	private ArrayList<City> cities;
	
	public Region() {
		cities = new ArrayList<>();
	}
	
	protected ArrayList<City> getCities() {
		return cities;
	}
	
	public void addCity(City city) throws InvalidCityException {
		if(!cities.contains(city)){
		cities.add(city);
		}
		else
			throw new InvalidCityException();
	}
	
	public boolean canTakeBonus(Player player) {
		return player.getBuiltEmporium().contains(cities);//&& bonus già preso
	}
	
	public abstract void takeBonus(Player player);
}
