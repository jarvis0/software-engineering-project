package it.polimi.ingsw.ps23.model.map;

import it.polimi.ingsw.ps23.model.GameColor;

public class PoliticCard extends Card {
	
	private GameColor color;
	
	public PoliticCard(GameColor color) {
		this.color = color;
	}
	
	@Override
	public String toString() {
		return color.toString();
	}

}
