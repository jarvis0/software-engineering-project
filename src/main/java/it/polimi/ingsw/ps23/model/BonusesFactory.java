package it.polimi.ingsw.ps23.model;

import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.model.map.BonusSlot;

public class BonusesFactory {
	
	public BonusSlot makeBonuses(String[] fields, String[] rawSlot, BonusSlot bonusSlot) {
		int i = 0;
		for(String rawSlotField : rawSlot) {
			int bonusValue = Integer.parseInt(rawSlotField);
			if(bonusValue > 0) {
				Bonus bonus = BonusCache.getBonus(fields[i], bonusValue);
				bonusSlot.addBonus(bonus);
			}
			i++;
		}
		return bonusSlot;
	}
	
}
