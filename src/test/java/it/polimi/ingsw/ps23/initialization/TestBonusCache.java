package it.polimi.ingsw.ps23.initialization;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.bonus.AdditionalMainActionBonus;
import it.polimi.ingsw.ps23.bonus.AssistantBonus;
import it.polimi.ingsw.ps23.bonus.Bonus;
import it.polimi.ingsw.ps23.bonus.BonusCache;
import it.polimi.ingsw.ps23.bonus.BuildingPermitBonus;
import it.polimi.ingsw.ps23.bonus.CoinBonus;
import it.polimi.ingsw.ps23.bonus.NobilityTrackStepBonus;
import it.polimi.ingsw.ps23.bonus.NullBonus;
import it.polimi.ingsw.ps23.bonus.PoliticCardBonus;
import it.polimi.ingsw.ps23.bonus.RecycleBuildingPermitBonus;
import it.polimi.ingsw.ps23.bonus.RecycleRewardTokenBonus;
import it.polimi.ingsw.ps23.bonus.VictoryPointBonus;

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
	
	@Test
	public void test() {
		
		List<Bonus> bonuses = new ArrayList<>();
		
		bonuses.add(new AssistantBonus(ASSISTANT));
		bonuses.get(bonuses.size() - 1).setValue(1);
		
		bonuses.add(new CoinBonus(COIN));
		bonuses.get(bonuses.size() - 1).setValue(1);
		
		bonuses.add(new VictoryPointBonus(VICTORY_POINT));
		bonuses.get(bonuses.size() - 1).setValue(1);
		
		bonuses.add(new PoliticCardBonus(POLITIC_CARD));
		bonuses.get(bonuses.size() - 1).setValue(1);
		
		bonuses.add(new AdditionalMainActionBonus(ADDITIONAL_MAIN_ACTION));
		bonuses.get(bonuses.size() - 1).setValue(1);
		
		bonuses.add(new NobilityTrackStepBonus(NOBILITY_TRACK_STEP));
		bonuses.get(bonuses.size() - 1).setValue(1);
		
		bonuses.add(new NullBonus(NULL_BONUS));
		bonuses.get(bonuses.size() - 1).setValue(1);
	
		bonuses.add(new RecycleRewardTokenBonus(RECYCLE_REWARD_TOKEN));
		bonuses.get(bonuses.size() - 1).setValue(1);

		bonuses.add(new BuildingPermitBonus(BUILDING_PERMIT));
		bonuses.get(bonuses.size() - 1).setValue(1);

		bonuses.add(new RecycleBuildingPermitBonus(RECYCLE_BUILDING_PERMIT));
		bonuses.get(bonuses.size() - 1).setValue(1);		

		BonusCache.loadCache();
		List<Bonus> cachedBonuses = new ArrayList<>();
		cachedBonuses.add(BonusCache.getBonus(ASSISTANT, 1));
		cachedBonuses.add(BonusCache.getBonus(COIN, 1));
		cachedBonuses.add(BonusCache.getBonus(VICTORY_POINT, 1));
		cachedBonuses.add(BonusCache.getBonus(POLITIC_CARD, 1));
		cachedBonuses.add(BonusCache.getBonus(ADDITIONAL_MAIN_ACTION, 1));
		cachedBonuses.add(BonusCache.getBonus(NOBILITY_TRACK_STEP, 1));
		cachedBonuses.add(BonusCache.getBonus(NULL_BONUS, 1));
		cachedBonuses.add(BonusCache.getBonus(RECYCLE_REWARD_TOKEN, 1));
		cachedBonuses.add(BonusCache.getBonus(BUILDING_PERMIT, 1));
		cachedBonuses.add(BonusCache.getBonus(RECYCLE_BUILDING_PERMIT, 1));
		
		int n = cachedBonuses.size();
		assertTrue(n == bonuses.size());
		for(int i = 0; i < n; i++) {
			assertTrue(cachedBonuses.get(i).getName() == bonuses.get(i).getName());
			assertTrue(cachedBonuses.get(i).getValue() == bonuses.get(i).getValue());
		}
	}

}
