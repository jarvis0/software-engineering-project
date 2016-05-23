package it.polimi.ingsw.ps23.model.map;

import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.bonus.VictoryPointBonus;

public class GroupColoredCity extends Region{

	//private Arraylist<ColorBonusTile> bonus;
	
	public GroupColoredCity(String id, VictoryPointBonus victoryPointBonus){
		super(id, victoryPointBonus);
		//create bonus tile per regione
		
	}

	@Override
	public void takeBonus(Player player) {
		//for(ogni bonus in bonustile lo devo aggiungere al giocatore)		
	}
	

}
