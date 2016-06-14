package it.polimi.ingsw.ps23.server.model.map.board;

import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.GameColor;

public class PoliticCard implements Card {
	
	private static final String MULTI = "multi";
	
	private GameColor color;
	
	public PoliticCard(GameColor color) {
		this.color = color;
	}
	
	public GameColor getColor() {
		return color;
	}
	
	public boolean isJoker() {
		return color.getName().equals(MULTI);
	}
	
	@Override
	public String toString() {
		return color.getName();
	}

}
