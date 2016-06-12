package it.polimi.ingsw.ps23.server.model.initialization;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.regions.RewardToken;
import it.polimi.ingsw.ps23.model.map.regions.RewardTokenSet;
import it.polimi.ingsw.ps23.server.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.server.model.initialization.BonusesFactory;

public class RewardTokenFactory {
	
	public RewardTokenSet makeRewardTokenSet(List<String[]> rawRewardTokens) {
		List<RewardToken> rewardTokens = new ArrayList<>();
		BonusCache.loadCache();
		String[] fields = rawRewardTokens.remove(rawRewardTokens.size() - 1);
		for(String[] rawRewardToken : rawRewardTokens) {
			rewardTokens.add((RewardToken) new BonusesFactory().makeBonuses(fields, rawRewardToken, new RewardToken()));
		}
		return new RewardTokenSet(rewardTokens);
	}
	
}
