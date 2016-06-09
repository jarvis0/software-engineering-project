package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.map.BonusSlot;

public class NobilityTrackStep implements BonusSlot {

	private List<Bonus> bonuses;
	
	public NobilityTrackStep() {
		bonuses = new ArrayList<>();
	}
	
	public void useBonus(Player player, TurnHandler turnHandler) {
		for(Bonus bonus : bonuses) {
			try {
				bonus.updateBonus(player, turnHandler);
			} catch (InsufficientResourcesException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void addBonus(Bonus bonus) {
		bonuses.add(bonus);		
	}
	
	@Override
	public String toString() {
		return bonuses.toString();
	}

}
