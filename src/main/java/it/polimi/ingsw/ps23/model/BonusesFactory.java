package it.polimi.ingsw.ps23.model;

import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.model.map.BonusCard;

public class BonusesFactory {
	
	public BonusCard makeBonuses(String[] fields, String[] rawRewardToken, BonusCard bonusCard) {
		int i = 0;
		for(String rawRewardTokenField : rawRewardToken) {
			int bonusValue = Integer.parseInt(rawRewardTokenField);
			if(bonusValue > 0) {
				Bonus bonus = BonusCache.getBonus(fields[i], bonusValue);
				bonusCard.addBonus(bonus);
				i++;
			}
		}
		return bonusCard;
	}
}
