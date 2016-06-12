package it.polimi.ingsw.ps23.server.model.map.board;

import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;

public class King {
	
	private City position;
	private Council council;
	
	public King(City position, Council council) {
		this.position = position;
		this.council = council;
	}
	
	@Override
	public String toString() {
		return "King's position: " + position.toString() + "\n" + council.toString();
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
}
