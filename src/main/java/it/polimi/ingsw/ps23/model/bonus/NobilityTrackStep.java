package it.polimi.ingsw.ps23.model.bonus;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.BonusSlot;

public class NobilityTrackStep implements BonusSlot{

	private List<Bonus> bonus;
	
	public NobilityTrackStep() {
		bonus = new ArrayList<>();
	}
	
	@Override
	public void addBonus(Bonus bonus) {
		this.bonus.add(bonus);		
	}
	
	@Override
	public String toString() {
		return bonus.toString();
	}

}
