package it.polimi.ingsw.ps23.model.map;

import it.polimi.ingsw.ps23.model.GameColor;

public class Councillor {
	
	GameColor color;
	
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
