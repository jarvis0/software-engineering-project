package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class PermissionCard extends Card implements BonusSlot {

	private ArrayList<Bonus> bonus;
	private ArrayList<String> citiesNames;
	
	public PermissionCard() {
		bonus = new ArrayList<>();
		citiesNames = new ArrayList<>();//?
	}
	
	public void addBonus(Bonus bonus) {
		this.bonus.add(bonus);
	}
	
	public void addCity(String cityName) {
		this.citiesNames.add(cityName);
	}
	
	@Override
	public String toString() {
		return bonus.toString() + citiesNames.toString();
	}
}
