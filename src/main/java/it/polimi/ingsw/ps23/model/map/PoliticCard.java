package it.polimi.ingsw.ps23.model.map;

import it.polimi.ingsw.ps23.model.GameColor;

public class PoliticCard extends Card {
	
	private static final String JOLLY = "multi";
	
	private GameColor color;
	
	public PoliticCard(GameColor color) {
		this.color = color;
	}
	
	public GameColor getColor() {
		return color;
	}
	
	public boolean isJolly () {
		return color.getName().equals(JOLLY);
	}
	
	@Override
	public String toString() {
		return color.toString();
	}

}
