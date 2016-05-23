package it.polimi.ingsw.ps23.model;

import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.model.map.BonusCard;

public class BonusesFactory {
	
	public BonusCard makeBonuses(String[] fields, String[] rawSlot, BonusCard bonusCard) {
		int i = 0;
		for(String rawSlotField : rawSlot) {
			int bonusValue = Integer.parseInt(rawSlotField);
			if(bonusValue > 0) {
				Bonus bonus = BonusCache.getBonus(fields[i], bonusValue);
				bonusCard.addBonus(bonus);
				i++;
			}
		}
		return bonusCard;
	}
}
