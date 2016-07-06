package it.polimi.ingsw.ps23.server.model.initialization;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.bonus.BonusCache;
import it.polimi.ingsw.ps23.server.model.initialization.BonusesBuilder;
import it.polimi.ingsw.ps23.server.model.map.regions.RewardToken;
import it.polimi.ingsw.ps23.server.model.map.regions.RewardTokensSet;

class RewardTokensBuilder {
	
	RewardTokensSet makeRewardTokenSet(List<String[]> rawRewardTokens, BonusCache bonusCache) {
		List<RewardToken> rewardTokens = new ArrayList<>();
		String[] fields = rawRewardTokens.remove(rawRewardTokens.size() - 1);
		for(String[] rawRewardToken : rawRewardTokens) {
			rewardTokens.add((RewardToken) new BonusesBuilder(bonusCache).makeBonuses(fields, rawRewardToken, new RewardToken()));
		}
		return new RewardTokensSet(rewardTokens);
	}
	
}
