package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;
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
	
	public void useBonus(Player player, TurnHandler turnHandler) {
		for (Bonus bonus : bonuses) {
			try {
		bonus.updateBonus(player, turnHandler);
			} catch (InsufficientResourcesException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String toString() {
		return bonuses.toString() + cities.toString();
	}

	
}
