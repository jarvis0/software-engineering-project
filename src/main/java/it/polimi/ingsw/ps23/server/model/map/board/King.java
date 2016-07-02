package it.polimi.ingsw.ps23.server.model.map.board;

import java.io.Serializable;

import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
/**
 * Provide methods to use the king during a game
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public class King implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2781939603449290246L;
	private City position;
	private Council council;
	/**
	 * Construct the king in the current game setting the starting position and the starting council
	 * @param position - starting position of the king
	 * @param council - starting council of the king
	 */
	public King(City position, Council council) {
		this.position = position;
		this.council = council;
	}
	
	public City getPosition(){
		return position;
	}
	
	public Council getCouncil() {
		return council;
	}

	public void setNewPosition(City arriveCity) {
		position = arriveCity;		
	}
	
	@Override
	public String toString() {
		return "King's position: " + position.toString() + "\n" + council.toString();
	}
	
}