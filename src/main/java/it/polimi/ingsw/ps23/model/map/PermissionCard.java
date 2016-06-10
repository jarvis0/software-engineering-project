package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class PermissionCard implements Card, BonusSlot {

	private List<Bonus> bonuses;
	private List<City> cities;
	
	public PermissionCard() {
		bonuses = new ArrayList<>();
		cities = new ArrayList<>();
	}
	
	@Override
	public void addBonus(Bonus bonus) {
		this.bonuses.add(bonus);
	}
	
	public void addCity(City city) {
		this.cities.add(city);
	}

	public void useBonus(Game game, TurnHandler turnHandler) {
		for (Bonus bonus : bonuses) {
			try {
				bonus.updateBonus(game, turnHandler);
			} catch (InsufficientResourcesException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String toString() {
		String print = bonuses.toString() + " ";
		boolean firstCity = true;
		for(City city : cities) {
			if(!firstCity) {
				print += " ~ " + city.getName();
			}
			else {
				print += city.getName();
				firstCity = false;
			}
		}
		return print;
	}
	
}
