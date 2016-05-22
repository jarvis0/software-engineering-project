package it.polimi.ingsw.ps23.model;

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
	
	public static Bonus getBonus(String bonusId) {
		Bonus cachedBonus = bonusMap.get(bonusId);
		return (Bonus) cachedBonus.clone();
	}
	private static void putBonus(Bonus bonus, String Id) {
		bonus.setId(Id);
		bonusMap.put(bonus.getId(), bonus);
	}
	
	public static void loadCache() {
		AssistantBonus assistantBonus = new AssistantBonus();
		putBonus(assistantBonus, ASSISTANT);
		
		CoinBonus coinBonus = new CoinBonus();
		putBonus(coinBonus, COIN);
		
		VictoryPointBonus victoryPointBonus = new VictoryPointBonus();
		putBonus(victoryPointBonus, VICTORY_POINT);
		
		PoliticCardBonus politicCardBonus = new PoliticCardBonus();
		putBonus(politicCardBonus, POLITIC_CARD);
		
		AdditionalMainActionBonus additionalMainActionBonus = new AdditionalMainActionBonus();
		putBonus(additionalMainActionBonus, ADDITIONAL_MAIN_ACTION);
		
		NobilityTrackStepBonus nobilityTrackStepBonus = new NobilityTrackStepBonus();
		putBonus(nobilityTrackStepBonus, NOBILITY_TRACK_STEP);
	}
	
}
