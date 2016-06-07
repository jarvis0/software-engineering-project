package it.polimi.ingsw.ps23.model.map;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.GameColor;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;

public class NormalCity extends City {
	
	RewardToken rewardToken;
	
	public NormalCity(String name, GameColor color, RewardToken rewardToken){ 
		super(name, color);
		this.rewardToken = rewardToken;
	}
	
	public void useRewardToken(Game game, TurnHandler turnHandler) {
		rewardToken.useBonus(game, turnHandler);
	}
	
	@Override
	public String toString() {
		return getName() + " " + getColor() + " " + rewardToken;
	}
	
	
}
