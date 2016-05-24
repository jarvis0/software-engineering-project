package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class PermissionCard extends Card implements BonusSlot {

	private ArrayList<Bonus> bonuses;
	private ArrayList<String> citiesNames;
	private ArrayList<String> regionsNames;
	
	public PermissionCard() {
		bonuses = new ArrayList<>();
		citiesNames = new ArrayList<>();
	}
	
	public void addBonus(Bonus bonus) {
		this.bonuses.add(bonus);
	}
	
	public void addCity(String cityName) {
		this.citiesNames.add(cityName);
	}
	
	@Override
	public String toString() {
		return bonuses.toString() + citiesNames.toString();
	}
}
