package it.polimi.ingsw.ps23.server.model.map.regions;

import it.polimi.ingsw.ps23.server.model.map.GameColor;

public class Councillor {
	
	private GameColor color;
	
	public Councillor(GameColor color) {
		this.color = color;
	}
	
	public GameColor getColor() {
		return color;
	}
	
	public String getColorName() {
		return color.getName();
	}
	
	@Override
	public String toString() {
		return color.toString();
	}

}
