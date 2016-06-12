package it.polimi.ingsw.ps23.bonus;

import java.util.HashMap;

import it.polimi.ingsw.ps23.bonus.AdditionalMainActionBonus;
import it.polimi.ingsw.ps23.bonus.AssistantBonus;
import it.polimi.ingsw.ps23.bonus.Bonus;
import it.polimi.ingsw.ps23.bonus.CoinBonus;
import it.polimi.ingsw.ps23.bonus.NobilityTrackStepBonus;
import it.polimi.ingsw.ps23.bonus.PoliticCardBonus;
import it.polimi.ingsw.ps23.bonus.VictoryPointBonus;

public class BonusCache {

	private static HashMap<String, Bonus> bonusesMap = new HashMap<>();
	private static final String ASSISTANT = "assistant";
	private static final String COIN = "coin";
	private static final String VICTORY_POINT = "victoryPoint";
	private static final String POLITIC_CARD = "politicCard";
	private static final String ADDITIONAL_MAIN_ACTION = "additionalMainAction";
	private static final String NOBILITY_TRACK_STEP = "nobilityTrackStep";
	private static final String NULL_BONUS = "nullBonus";
	private static final String RECYCLE_REWARD_TOKEN = "recycleRewardToken";
	private static final String BUILDING_PERMIT = "buildingPermit";
	private static final String RECYCLE_BUILDING_PERMIT = "recycleBuildingPermit";

	private BonusCache() {
	}
	
	public static Bonus getBonus(String bonusName, int value) {
		Bonus cachedBonus = bonusesMap.get(bonusName);
		Bonus bonus = (Bonus) cachedBonus.clone();
		bonus.setValue(value);
		return bonus;
	}
	
	private static void putBonus(Bonus bonus) {
		bonusesMap.put(bonus.getName(), bonus);
	}
	
	public static void loadCache() {
		
		Bonus assistantBonus = new AssistantBonus(ASSISTANT);
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
			
		Bonus nullBonus = new NullBonus(NULL_BONUS);
		putBonus(nullBonus);
		
		Bonus recycleRewardToken = new RecycleRewardTokenBonus(RECYCLE_REWARD_TOKEN);
		putBonus(recycleRewardToken);
		
		Bonus buildingPermitBonus = new BuildingPermitBonus(BUILDING_PERMIT);
		putBonus(buildingPermitBonus);
		
		Bonus recycleBuildingPermitBonus = new RecycleBuildingPermitBonus(RECYCLE_BUILDING_PERMIT);
		putBonus(recycleBuildingPermitBonus);
		
	}
	
}
