package it.polimi.ingsw.ps23.server.model.map.board;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.Region;

public class GroupColoredCity extends Region {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6371457848504637219L;

	public GroupColoredCity(String name, Bonus victoryPointsBonus) {
		super(name, victoryPointsBonus);
	}
	
}
