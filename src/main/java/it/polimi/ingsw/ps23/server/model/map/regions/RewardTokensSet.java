package it.polimi.ingsw.ps23.server.model.map.regions;

import java.util.Collections;
import java.util.List;
/**
 * Provide methods to create a set of {@link RewardToken} and shuffling it.
 * @author Giuseppe Mascellaro
 *
 */
public class RewardTokensSet {

	private List<RewardToken> rewardTokens;
	/**
	 * Constructs a reward token set starting from a list of {@link RewardToken}.
	 * @param rewardTokenSet - list of reward tokens
	 */
	public RewardTokensSet(List<RewardToken> rewardTokenSet) {
		Collections.shuffle(rewardTokenSet);
		this.rewardTokens = rewardTokenSet;
	}
	/**
	 * Calculate the amount of {@link RewardToken} present in the list.
	 * @return ammount of reward tokens.
	 */
	public int rewardTokenSize() {
		return rewardTokens.size();
	}
	
	/**
	 * Removes the reward token bonus to the set of reward tokens.
	 * @param index - index of the reward token bonuses to be removed
	 * @return the removed reward token bonus at the specified index.
	 */
	public RewardToken removeRewardToken(int index) {
		return rewardTokens.remove(index);
	}
	
}
