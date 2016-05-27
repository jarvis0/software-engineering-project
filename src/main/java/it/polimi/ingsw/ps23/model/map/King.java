package it.polimi.ingsw.ps23.model.map;

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
}
