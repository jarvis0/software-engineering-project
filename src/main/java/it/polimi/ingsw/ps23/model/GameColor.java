package it.polimi.ingsw.ps23.model;

import java.awt.Color;

public class GameColor {
	
	private Color color;
	private String colorName;
	
	public GameColor(String colorName, String colorHex) {
		this.colorName = colorName;
		color = Color.decode(colorHex);
	}
	
	@Override
	public String toString() {
		return colorName;
	}
}