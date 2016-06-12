package it.polimi.ingsw.ps23.server.model.map.regions;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.GameColor;

public class NormalCity extends City {
	
	private RewardToken rewardToken;
	
	public NormalCity(String name, GameColor color, RewardToken rewardToken) { 
		super(name, color);
		this.rewardToken = rewardToken;
	}
	
	public void useRewardToken(Game game, TurnHandler turnHandler) {
		rewardToken.useBonus(game, turnHandler);
	}
	
	public boolean hasNobilityTrackBonus() {
		return rewardToken.hasNobilityTrackBonus();
	}
	
	@Override
	public String toString() {
		return getName() + " " + getColor() + " " + rewardToken + " " + getEmporiumsPlayersList();
	}
	
}
