package it.polimi.ingsw.ps23.model.map;

public class King {
	
	private City position;
	
	public King(City position) {
		this.position = position;
	}
	
	@Override
	public String toString() {
		return "King's position: " + position.toString();
	}
}
