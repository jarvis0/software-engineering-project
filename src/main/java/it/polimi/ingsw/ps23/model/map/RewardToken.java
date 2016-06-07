package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class RewardToken implements BonusSlot {
	
	private List<Bonus> bonuses;
	
	public RewardToken() {
		bonuses = new ArrayList<>();
	}
	
	public void addBonus(Bonus bonus) {
		this.bonuses.add(bonus);
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
		return bonuses.toString();
	}


}
