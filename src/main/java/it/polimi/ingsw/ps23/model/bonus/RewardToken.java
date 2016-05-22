package it.polimi.ingsw.ps23.model.bonus;

import java.util.ArrayList;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;

public class RewardToken {
	
	private ArrayList<Bonus> bonusToken;
	
	public void addBonus(Bonus bonus) {
		bonusToken.add(bonus);
	}
	
	public void takeBonus(Player player) throws InsufficientResourcesException {
		for (Bonus bonus : bonusToken) {
			bonus.updateBonus(player);
		}
	}

}
