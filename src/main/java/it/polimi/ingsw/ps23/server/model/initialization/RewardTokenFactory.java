package it.polimi.ingsw.ps23.server.model.initialization;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.server.model.initialization.BonusesFactory;
import it.polimi.ingsw.ps23.server.model.map.regions.RewardToken;
import it.polimi.ingsw.ps23.server.model.map.regions.RewardTokenSet;

class RewardTokenFactory {
	
	RewardTokenSet makeRewardTokenSet(List<String[]> rawRewardTokens, BonusCache bonusCache) {
		List<RewardToken> rewardTokens = new ArrayList<>();
		String[] fields = rawRewardTokens.remove(rawRewardTokens.size() - 1);
		for(String[] rawRewardToken : rawRewardTokens) {
			rewardTokens.add((RewardToken) new BonusesFactory(bonusCache).makeBonuses(fields, rawRewardToken, new RewardToken()));
		}
		return new RewardTokenSet(rewardTokens);
	}
	
}
