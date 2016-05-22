package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.Player;

public abstract class Region {
	
	private ArrayList<City> cities;
	
	protected ArrayList<City> getCities() {
		return cities;
	}

	public Region() {
		cities = new ArrayList<>();
	}
	
	public void addCity(City city) throws InvalidCityException {
		if(!cities.contains(city)){
		cities.add(city);
		}
		else
			throw new InvalidCityException();
	}
	
	public boolean canTakeBonus(Player player)
	{
		//return /*player.metodo per ottenere le città in cui ha già costruito.Contains(cities) || bonus già preso*/
		return false;
	}
	
	public abstract void takeBonus(Player player);
}
