package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class RewardToken implements BonusSlot {
	
	private List<Bonus> bonus;
	
	public RewardToken() {
		bonus = new ArrayList<>();
	}
	
	public void addBonus(Bonus bonus) {
		this.bonus.add(bonus);
	}
	
	public void useBonus(Player player) {
		for (Bonus bonus : bonus) {
			try {
		bonus.updateBonusReward(player);
			} catch (InsufficientResourcesException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String toString() {
		return bonus.toString();
	}


}
