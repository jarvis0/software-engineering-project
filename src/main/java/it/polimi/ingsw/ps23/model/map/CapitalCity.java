package it.polimi.ingsw.ps23.model.map;

import it.polimi.ingsw.ps23.model.GameColor;
import it.polimi.ingsw.ps23.model.Player;

public class CapitalCity extends City {
	
	public CapitalCity(GameColor color){
			super(color);
	}

	@Override
	public String toString() {
		return super.getColor();
	}
	
	@Override
	public void buildEmporium(Player player) {
		// TODO Auto-generated method stub
		
	}
	

}
