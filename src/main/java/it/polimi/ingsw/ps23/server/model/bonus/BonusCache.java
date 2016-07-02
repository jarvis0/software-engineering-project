package it.polimi.ingsw.ps23.server.model.bonus;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.ps23.server.model.bonus.AdditionalMainActionBonus;
import it.polimi.ingsw.ps23.server.model.bonus.AssistantBonus;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.CoinBonus;
import it.polimi.ingsw.ps23.server.model.bonus.NobilityTrackStepBonus;
import it.polimi.ingsw.ps23.server.model.bonus.PoliticCardBonus;
import it.polimi.ingsw.ps23.server.model.bonus.VictoryPointBonus;
/**
 * Provide methods to create a specific type of bonus starting from a string
 * @author Giuseppe Mascellaro
 *
 */
public class BonusCache {

	private Map<String, Bonus> bonusesMap;
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
	/**
	 * construct the bonus cache mapping all the bonuses.
	 */
	public BonusCache() {
		bonusesMap = new HashMap<>();
		loadCache();
	}
	/**
	 * Starting from a string, the methods create the corresponding {@link Bonus}.
	 * @param bonusName - the name of the bonus
	 * @param value - value of bonus
	 * @return the created bonus
	 */
	public Bonus getBonus(String bonusName, int value) {
		Bonus cachedBonus = bonusesMap.get(bonusName);
		Bonus bonus = (Bonus) cachedBonus.clone();
		bonus.setValue(value);
		return bonus;
	}
	
	private void putBonus(Bonus bonus) {
		bonusesMap.put(bonus.getName(), bonus);
	}
	
	private void loadCache() {
		
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
