package it.polimi.ingsw.ps23.model.bonus;

import java.util.HashMap;

import it.polimi.ingsw.ps23.model.bonus.AdditionalMainActionBonus;
import it.polimi.ingsw.ps23.model.bonus.AssistantBonus;
import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.bonus.CoinBonus;
import it.polimi.ingsw.ps23.model.bonus.NobilityTrackStepBonus;
import it.polimi.ingsw.ps23.model.bonus.PoliticCardBonus;
import it.polimi.ingsw.ps23.model.bonus.VictoryPointBonus;

//static? - flyweight
public class BonusCache {

	private static HashMap<String, Bonus> bonusMap  = new HashMap<>();
	private static final String ASSISTANT = "assistant";
	private static final String COIN = "coin";
	private static final String VICTORY_POINT = "victoryPoint";
	private static final String POLITIC_CARD = "politicCard";
	private static final String ADDITIONAL_MAIN_ACTION = "additionalMainAction";
	private static final String NOBILITY_TRACK_STEP = "nobilityTrackStep";
	private static final String RECYCLE_REWARD_TOKEN = "recycleRewardToken";
	private static final String BUILDING_PERMIT = "buildingPermit";
	private static final String RECYCLE_BUILDING_PERMIT = "recycleBuildingPermit";
	private static final String NULL_BONUS = "nullBonus";

	private BonusCache() {
	}
	
	public static Bonus getBonus(String bonusId, int value) {
		Bonus cachedBonus = bonusMap.get(bonusId);
		Bonus bonus = (Bonus) cachedBonus.clone();
		bonus.setValue(value);
		return bonus;
	}
	private static void putBonus(Bonus bonus) {
		bonusMap.put(bonus.getName(), bonus);
	}
	
	public static void loadCache() {
		AssistantBonus assistantBonus = new AssistantBonus(ASSISTANT);
		putBonus(assistantBonus);
		
		Bonus coinBonus = new CoinBonus(COIN);
		putBonus(coinBonus);
		
		Bonus victoryPointBonus = new VictoryPointBonus(VICTORY_POINT);
		putBonus(victoryPointBonus);
		
		Bonus politicCardBonus = new PoliticCardBonus(POLITIC_CARD);
		putBonus(politicCardBonus);
		
		Bonus additionalMainActionBonus = new AdditionalMainActionBonus(ADDITIONAL_MAIN_ACTION);
		putBonus(additionalMainActionBonus);
		
		Bonus nobilityTrackStepBonus = new NobilityTrackStepBonus(NOBILITY_TRACK_STEP);
		putBonus(nobilityTrackStepBonus);
		
		Bonus recycleRewardToken = new RecycleRewardToken(RECYCLE_REWARD_TOKEN);
		putBonus(recycleRewardToken);
		
		Bonus buildingPermitBonus = new BuildingPermitBonus(BUILDING_PERMIT);
		putBonus(buildingPermitBonus);
		
		Bonus recycleBuildingPermitBonus = new RecycleBuildingPermitBonus(RECYCLE_BUILDING_PERMIT);
		putBonus(recycleBuildingPermitBonus);
		
		Bonus nullBonus = new NullBonus(NULL_BONUS);
		putBonus(nullBonus);
		
	}
	
}
