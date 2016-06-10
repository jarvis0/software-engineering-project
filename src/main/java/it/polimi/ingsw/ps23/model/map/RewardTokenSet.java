package it.polimi.ingsw.ps23.model.map;

import java.util.Collections;
import java.util.List;

public class RewardTokenSet {

	private List<RewardToken> rewardTokens;
	
	public RewardTokenSet(List<RewardToken> rewardTokenSet) {
		Collections.shuffle(rewardTokenSet);
		this.rewardTokens = rewardTokenSet;
	}

	public List<RewardToken> getRewardTokenSet() {
		return rewardTokens;
	}
	
	public int rewardTokenSize() {
		return rewardTokens.size();
	}
	
	public RewardToken removeRewardToken(int index) {
		return rewardTokens.remove(index);
	}
	
	@Override
	public String toString() {
		return this.getRewardTokenSet().toString();
	}
	
}
