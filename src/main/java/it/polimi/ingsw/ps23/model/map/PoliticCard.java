package it.polimi.ingsw.ps23.model.map;

import it.polimi.ingsw.ps23.model.GameColor;

public class PoliticCard implements Card {
	
	private static final String MULTI = "multi";
	
	private GameColor color;
	
	public PoliticCard(GameColor color) {
		this.color = color;
	}
	
	public GameColor getColor() {
		return color;
	}
	
	public boolean isJolly () {
		return color.getName().equals(MULTI);
	}
	
	@Override
	public String toString() {
		return color.toString();
	}

}
