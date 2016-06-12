package it.polimi.ingsw.ps23.model;

import it.polimi.ingsw.ps23.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.model.bonus.BonusSlot;

public class BonusesFactory {
	
	private static final String NULL_BONUS_NAME = "nullBonus";
	private static final int NULL_BONUS_VALUE = 0;
	
	public BonusSlot makeBonuses(String[] fields, String[] rawSlot, BonusSlot bonusSlot) {
		boolean nullBonus = true;
		int i = 0;
		for(String rawSlotField : rawSlot) {
			int bonusValue = Integer.parseInt(rawSlotField);
			if(bonusValue > 0) {
				nullBonus = false;
				bonusSlot.addBonus(BonusCache.getBonus(fields[i], bonusValue));
			}
			i++;
		}
		if(nullBonus) {
			bonusSlot.addBonus(BonusCache.getBonus(NULL_BONUS_NAME, NULL_BONUS_VALUE));
		}
		return bonusSlot;
	}
	
}
