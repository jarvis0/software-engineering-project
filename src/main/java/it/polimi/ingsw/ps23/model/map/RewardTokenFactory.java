package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.BonusesFactory;
import it.polimi.ingsw.ps23.model.bonus.BonusCache;

public class RewardTokenFactory {

	private ArrayList<RewardToken> rewardTokens;
	
	public RewardTokenFactory() {
		rewardTokens = new ArrayList<>();
	}
	
	public List<RewardToken> makeRewardTokens(List<String[]> rawRewardTokens) {
		BonusCache.loadCache();
		String[] fields = rawRewardTokens.remove(rawRewardTokens.size() - 1);
		for(String[] rawRewardToken : rawRewardTokens) {
			rewardTokens.add((RewardToken) new BonusesFactory().makeBonuses(fields, rawRewardToken, new RewardToken()));
		}
		return new RewardTokens(rewardTokens).getRewardTokens();
	}
	
}
