package it.polimi.ingsw.ps23.server.model.initialization;

import it.polimi.ingsw.ps23.server.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.server.model.bonus.BonusSlot;

class BonusesFactory {
	
	private static final String NULL_BONUS_NAME = "nullBonus";
	private static final int NULL_BONUS_VALUE = 0;
	
	private BonusCache bonusCache;
	
	BonusesFactory(BonusCache bonusCache) {
		this.bonusCache = bonusCache;
	}
	
	BonusSlot makeBonuses(String[] fields, String[] rawSlot, BonusSlot bonusSlot) {
		boolean nullBonus = true;
		int i = 0;
		for(String rawSlotField : rawSlot) {
			int bonusValue = Integer.parseInt(rawSlotField);
			if(bonusValue > 0) {
				nullBonus = false;
				bonusSlot.addBonus(bonusCache.getBonus(fields[i], bonusValue));
			}
			i++;
		}
		if(nullBonus) {
			bonusSlot.addBonus(bonusCache.getBonus(NULL_BONUS_NAME, NULL_BONUS_VALUE));
		}
		return bonusSlot;
	}
	
}
