package it.polimi.ingsw.ps23.model.bonus;

import java.util.HashMap;

import it.polimi.ingsw.ps23.model.bonus.AdditionalMainActionBonus;
import it.polimi.ingsw.ps23.model.bonus.AssistantBonus;
import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.bonus.CoinBonus;
import it.polimi.ingsw.ps23.model.bonus.NobilityTrackStepBonus;
import it.polimi.ingsw.ps23.model.bonus.PoliticCardBonus;
import it.polimi.ingsw.ps23.model.bonus.VictoryPointBonus;

public class BonusCache {

	private static HashMap<String, Bonus> bonusMap  = new HashMap<>();
	private static final String ASSISTANT = "assistant";
	private static final String COIN = "coin";
	private static final String VICTORY_POINT = "victoryPoint";
	private static final String POLITIC_CARD = "politicCard";
	private static final String ADDITIONAL_MAIN_ACTION = "additionalMainAction";
	private static final String NOBILITY_TRACK_STEP = "nobilityTrackStep";
	
	public static Bonus getBonus(String bonusId, int value) {
		Bonus cachedBonus = bonusMap.get(bonusId);
		Bonus bonus = (Bonus) cachedBonus.clone();
		bonus.setValue(value);
		return bonus;
	}
	private static void putBonus(Bonus bonus) {
		bonusMap.put(bonus.getId(), bonus);
	}
	
	public static void loadCache() {
		AssistantBonus assistantBonus = new AssistantBonus(ASSISTANT);
		putBonus(assistantBonus);
		
		CoinBonus coinBonus = new CoinBonus(COIN);
		putBonus(coinBonus);
		
		VictoryPointBonus victoryPointBonus = new VictoryPointBonus(VICTORY_POINT);
		putBonus(victoryPointBonus);
		
		PoliticCardBonus politicCardBonus = new PoliticCardBonus(POLITIC_CARD);
		putBonus(politicCardBonus);
		
		AdditionalMainActionBonus additionalMainActionBonus = new AdditionalMainActionBonus(ADDITIONAL_MAIN_ACTION);
		putBonus(additionalMainActionBonus);
		
		NobilityTrackStepBonus nobilityTrackStepBonus = new NobilityTrackStepBonus(NOBILITY_TRACK_STEP);
		putBonus(nobilityTrackStepBonus);
	}
	
}
