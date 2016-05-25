package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.bonus.Bonus;

public abstract class Region {
	
	private String id;
	private List<City> cities;
	private Bonus victoryPointsBonus;
	
	public Region(String id, Bonus victoryPointBonus) {
		cities = new ArrayList<>();
		this.id = id;
		this.victoryPointsBonus = victoryPointBonus;
	}
	
	protected List<City> getCities() {
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
	
	protected Bonus getBonus() {
		return victoryPointsBonus;
	}
	
	public String getId() {
		return id;
	}
	
	//public abstract void takeBonus(Player player);

	
	@Override
	public String toString() {
		return id + ": " + cities + "\n" + "[BonusTile: " + victoryPointsBonus + "]" + "\n"; 
	}
}
