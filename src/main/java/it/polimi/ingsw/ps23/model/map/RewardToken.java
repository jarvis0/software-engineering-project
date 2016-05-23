package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class RewardToken implements BonusSlot {
	
	private ArrayList<Bonus> bonus;
	
	public RewardToken() {
		bonus = new ArrayList<>();
	}
	
	public void addBonus(Bonus bonus) {
		this.bonus.add(bonus);
	}
	
	@Override
	public String toString() {
		return bonus.toString();
	}

}
