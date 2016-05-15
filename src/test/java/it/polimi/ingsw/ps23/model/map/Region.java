package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.Player;

public abstract class Region {
	
	protected ArrayList<City> cities = new ArrayList<City>();
	
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
