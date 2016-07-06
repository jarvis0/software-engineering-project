package it.polimi.ingsw.ps23.server.model.initialization;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.bonus.AdditionalMainActionBonus;
import it.polimi.ingsw.ps23.server.model.bonus.AssistantBonus;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.server.model.bonus.BuildingPermitBonus;
import it.polimi.ingsw.ps23.server.model.bonus.CoinBonus;
import it.polimi.ingsw.ps23.server.model.bonus.NobilityTrackStepBonus;
import it.polimi.ingsw.ps23.server.model.bonus.NullBonus;
import it.polimi.ingsw.ps23.server.model.bonus.PoliticCardBonus;
import it.polimi.ingsw.ps23.server.model.bonus.RealBonus;
import it.polimi.ingsw.ps23.server.model.bonus.RecycleBuildingPermitBonus;
import it.polimi.ingsw.ps23.server.model.bonus.RecycleRewardTokenBonus;
import it.polimi.ingsw.ps23.server.model.bonus.VictoryPointBonus;
/**
 * Tests the construction of some bonuses in {@link BonusCache} when the game is in the initialization phase.
 * @author Giuseppe Mascellaro
 *
 */
public class TestBonusCache {
	
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
	
	List<Bonus> bonuses;
	
	@Test
	public void test() {
		bonuses = new ArrayList<>();
		bonuses.add(new AssistantBonus(ASSISTANT));
		setValue();
		
		bonuses.add(new CoinBonus(COIN));
		setValue();
		
		bonuses.add(new VictoryPointBonus(VICTORY_POINT));
		setValue();
		
		bonuses.add(new PoliticCardBonus(POLITIC_CARD));
		setValue();
		
		bonuses.add(new AdditionalMainActionBonus(ADDITIONAL_MAIN_ACTION));
		setValue();
		
		bonuses.add(new NobilityTrackStepBonus(NOBILITY_TRACK_STEP));
		setValue();
		
		bonuses.add(new NullBonus(NULL_BONUS));
		setValue();
	
		bonuses.add(new RecycleRewardTokenBonus(RECYCLE_REWARD_TOKEN));
		setValue();

		bonuses.add(new BuildingPermitBonus(BUILDING_PERMIT));
		setValue();
		
		bonuses.add(new RecycleBuildingPermitBonus(RECYCLE_BUILDING_PERMIT));
		setValue();

		BonusCache bonusCache = new BonusCache();
		List<Bonus> cachedBonuses = new ArrayList<>();
		cachedBonuses.add(bonusCache.getBonus(ASSISTANT, 1));
		cachedBonuses.add(bonusCache.getBonus(COIN, 1));
		cachedBonuses.add(bonusCache.getBonus(VICTORY_POINT, 1));
		cachedBonuses.add(bonusCache.getBonus(POLITIC_CARD, 1));
		cachedBonuses.add(bonusCache.getBonus(ADDITIONAL_MAIN_ACTION, 1));
		cachedBonuses.add(bonusCache.getBonus(NOBILITY_TRACK_STEP, 1));
		cachedBonuses.add(bonusCache.getBonus(NULL_BONUS, 1));
		cachedBonuses.add(bonusCache.getBonus(RECYCLE_REWARD_TOKEN, 1));
		cachedBonuses.add(bonusCache.getBonus(BUILDING_PERMIT, 1));
		cachedBonuses.add(bonusCache.getBonus(RECYCLE_BUILDING_PERMIT, 1));
		
		int n = cachedBonuses.size();
		assertTrue(n == bonuses.size());
		for(int i = 0; i < n; i++) {
			assertTrue(cachedBonuses.get(i).getName() == bonuses.get(i).getName());
			if(!bonuses.get(i).isNull()) {
				assertTrue(((RealBonus)cachedBonuses.get(i)).getValue() == ((RealBonus)bonuses.get(i)).getValue());
			}
		}
	}
	
	private void setValue() {
		if(!(bonuses.get(bonuses.size() - 1)).isNull()) {
			((RealBonus)bonuses.get(bonuses.size() - 1)).setValue(1);
		}
	}

}
