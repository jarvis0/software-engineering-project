package it.polimi.ingsw.ps23.model.map;

import it.polimi.ingsw.ps23.model.GameColor;
import it.polimi.ingsw.ps23.model.Player;

public class NormalCity extends City {
	
	RewardToken rewardToken;
	
	public NormalCity(GameColor color, RewardToken rewardToken){ 
		super(color);
		this.rewardToken = rewardToken;
	}
	
	@Override
	public String toString() {
		return super.getColor() + " " + rewardToken;
	}
	
	@Override
	public void buildEmporium(Player player) {
		// TODO Auto-generated method stub
		
	}

}
