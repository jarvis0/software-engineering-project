package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.bonus.Bonus;

public class BonusTile {
	
	private List<Bonus> bonuses;
	
	public BonusTile() {
		bonuses = new ArrayList<>();
	}
	
	public void addTile(Bonus bonus) {
		bonuses.add(bonus);
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
}
