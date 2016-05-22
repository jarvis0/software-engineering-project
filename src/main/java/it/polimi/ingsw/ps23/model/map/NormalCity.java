package it.polimi.ingsw.ps23.model.map;

import it.polimi.ingsw.ps23.model.GameColor;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.bonus.RewardToken;

public class NormalCity extends City {
	RewardToken cityToken;
	
	public NormalCity(String name, GameColor color, RewardToken cityToken){ 
		super(name, color);
		this.cityToken = cityToken;
	}

	@Override
	public void buildEmporium(Player player) {
		// TODO Auto-generated method stub
		
	}
}
