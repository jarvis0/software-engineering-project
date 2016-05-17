package it.polimi.ingsw.ps23.model.bonus;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.Player;

public class RewardToken {
	
	private ArrayList<Bonus> bonusToken;
	
	public RewardToken() {
		bonusToken = new ArrayList<>();
		//bonusToken.add(bonus);
	}
	
	public void takeBonus(Player player) {
		for (Bonus bonus : bonusToken) {
			bonus.updateBonus(player);
		}
	}

}
