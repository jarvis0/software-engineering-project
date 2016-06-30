package it.polimi.ingsw.ps23.server.model.map.regions;

import java.util.Collections;
import java.util.List;

public class RewardTokensSet {

	private List<RewardToken> rewardTokens;
	
	public RewardTokensSet(List<RewardToken> rewardTokenSet) {
		Collections.shuffle(rewardTokenSet);
		this.rewardTokens = rewardTokenSet;
	}

	public int rewardTokenSize() {
		return rewardTokens.size();
	}
	
	public RewardToken removeRewardToken(int index) {
		return rewardTokens.remove(index);
	}
	
}
