package it.polimi.ingsw.ps23.model;

import java.awt.Color;

public class GameColor {
	
	private Color color;
	private String colorName;
	
	public GameColor(String colorName, String colorHex) {
		this.colorName = colorName;
		color = Color.decode(colorHex);
	}
	
	public Color getHex() {
		return color;
	}
	
	@Override
	public String toString() {
		return colorName;
	}

	public String getName() {
		return colorName;
	}
	
	public boolean isSameColor(String other) {
		return other.equals(colorName);
	}
	
}