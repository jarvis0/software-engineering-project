package it.polimi.ingsw.ps23.model.map;

import it.polimi.ingsw.ps23.model.GameColor;

public abstract class City {
	
	private String name;
	private GameColor color;
	
	protected City(String name, GameColor color) {
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color.toString();
	}
	
}