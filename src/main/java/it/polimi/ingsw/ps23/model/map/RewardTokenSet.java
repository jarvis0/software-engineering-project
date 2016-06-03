package it.polimi.ingsw.ps23.model.map;

import java.util.Collections;
import java.util.List;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.bonus.Bonus;

//cambiare nome -> deck?
public class RewardTokenSet {

	private List<RewardToken> rewardTokenSet;
	
	public RewardTokenSet(List<RewardToken> rewardTokenSet) {
		Collections.shuffle(rewardTokenSet);
		this.rewardTokenSet = rewardTokenSet;
	}

	public List<RewardToken> getRewardTokenSet() {
		return rewardTokenSet;
	}
	
	public int rewardTokenSize() {
		return rewardTokenSet.size();
	}
	
	public RewardToken removeRewardToken(int index) {
		return rewardTokenSet.remove(index);
	}
	
	@Override
	public String toString() {
		return this.getRewardTokenSet().toString();
	}
	
}
