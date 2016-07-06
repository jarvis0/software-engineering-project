package it.polimi.ingsw.ps23.server.model.map.board;

import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.Region;
/**
 * Abstraction used to represent a group city by color
 * @author Alessandro Erba
 *
 */
public class GroupColoredCity extends Region {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6371457848504637219L;
	/**
	 * Constructs a group colored region taking the name of the color and the victory point of the tile
	 * @param name - name of the color of the region
	 * @param victoryPointsBonus - victory point of the bonus tile
	 */
	public GroupColoredCity(String name, Bonus victoryPointsBonus) {
		super(name, victoryPointsBonus);
	}
	
}
