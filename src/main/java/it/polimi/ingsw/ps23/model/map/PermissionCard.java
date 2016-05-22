package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class PermissionCard extends Card {

	private ArrayList<Bonus> bonus;
	
	public PermissionCard() {
		bonus = new ArrayList<>();
	}
	
	public void addBonus(Bonus bonus) {
		this.bonus.add(bonus);
	}
	
	@Override
	public String toString() {
		String output = new String();
		for(Bonus aBonus : bonus) {
			output = output + " " + aBonus.getId() + " " + aBonus.getValue();
		}
		return output;
	}
	
}
