package it.polimi.ingsw.ps23.model.map;

import java.util.Collections;
import java.util.List;

public class RewardTokens {

	private List<RewardToken> rewardTokens;
	
	public RewardTokens(List<RewardToken> rewardTokens) {
		Collections.shuffle(rewardTokens);
		this.rewardTokens = rewardTokens;
	}
	
	@Override
	public String toString() {
		return this.getRewardTokens().toString();
	}
	
	public List<RewardToken> getRewardTokens() {
		return rewardTokens;
	}
	
}
