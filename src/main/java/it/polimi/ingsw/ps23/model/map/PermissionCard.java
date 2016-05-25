package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class PermissionCard extends Card implements BonusSlot {

	private List<Bonus> bonuses;
	private List<City> cities;
	
	public PermissionCard() {
		bonuses = new ArrayList<>();
		cities = new ArrayList<>();
	}
	
	public void addBonus(Bonus bonus) {
		this.bonuses.add(bonus);
	}
	
	public void addCity(City city) {
		this.cities.add(city);
	}
	
	@Override
	public String toString() {
		return bonuses.toString() + cities.toString();
	}

	
}
