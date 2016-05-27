package it.polimi.ingsw.ps23.model.map;

import java.util.Collections;
import java.util.List;

//cambiare nome -> deck?
public class RewardTokens {

	private List<RewardToken> rewardTokens;
	
	public RewardTokens(List<RewardToken> rewardTokens) {
		Collections.shuffle(rewardTokens);
		this.rewardTokens = rewardTokens;
	}

	public List<RewardToken> getRewardTokens() {
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
		return this.getRewardTokens().toString();
	}
	
}
