package it.polimi.ingsw.ps23.initialization;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.server.model.initialization.RawObject;
import it.polimi.ingsw.ps23.server.model.initialization.RewardTokenFactory;
import it.polimi.ingsw.ps23.server.model.map.regions.RewardToken;
import it.polimi.ingsw.ps23.server.model.map.regions.RewardTokenSet;

public class TestLoadRewardTokenSet {

	private static final String TEST_CONFIGURATION_PATH = "src/test/java/configuration/";
	private static final String REWARD_TOKENS_CSV = "rewardTokens.csv";
	
	@Test
	public void test() {
		List<String[]> rawRewardTokens = new RawObject(TEST_CONFIGURATION_PATH + REWARD_TOKENS_CSV).getRawObject();
		RewardTokenSet rewardTokens = new RewardTokenFactory().makeRewardTokenSet(rawRewardTokens);
		int n = rewardTokens.rewardTokenSize();
		RewardToken rewardToken1 = rewardTokens.removeRewardToken(n - 1);
		assertTrue(n - 1 == rewardTokens.rewardTokenSize());
		RewardToken rewardToken2 = rewardTokens.removeRewardToken(n - 2);
		assertTrue(!rewardToken1.hasNobilityTrackBonus() && rewardToken2.hasNobilityTrackBonus());
		BonusCache.loadCache();
		Bonus bonus = BonusCache.getBonus("nobilityTrackStep", 1);
		rewardToken1.addBonus(bonus);
		assertTrue(rewardToken1.hasNobilityTrackBonus());
	}

}
