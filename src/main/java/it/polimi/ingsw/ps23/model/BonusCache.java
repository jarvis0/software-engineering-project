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
	public static void loadCache() {
		AssistantBonus assistantBonus = new AssistantBonus();
		assistantBonus.setId(ASSISTANT);
		bonusMap.put(assistantBonus.getId(), assistantBonus);
		
		CoinBonus coinBonus = new CoinBonus();
		coinBonus.setId(COIN);
		bonusMap.put(coinBonus.getId(), coinBonus);
		
		VictoryPointBonus victoryPointBonus = new VictoryPointBonus();
		victoryPointBonus.setId(VICTORY_POINT);
		bonusMap.put(victoryPointBonus.getId(), victoryPointBonus);
		
		PoliticCardBonus politicCardBonus = new PoliticCardBonus();
		politicCardBonus.setId(POLITIC_CARD);
		bonusMap.put(politicCardBonus.getId(), politicCardBonus);
		
		AdditionalMainActionBonus additionalMainActionBonus = new AdditionalMainActionBonus();
		additionalMainActionBonus.setId(ADDITIONAL_MAIN_ACTION);
		bonusMap.put(additionalMainActionBonus.getId(), additionalMainActionBonus);
		
		NobilityTrackStepBonus nobilityTrackStepBonus = new NobilityTrackStepBonus();
		nobilityTrackStepBonus.setId(NOBILITY_TRACK_STEP);
		bonusMap.put(nobilityTrackStepBonus.getId(), nobilityTrackStepBonus);
	}
}
