package it.polimi.ingsw.ps23.server.model.map;

import java.awt.Color;
import java.io.Serializable;

public class GameColor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7625811392414498777L;
	private Color color;
	private String colorName;
	
	public GameColor(String colorName, String colorHex) {
		this.colorName = colorName;
		color = Color.decode(colorHex);
	}
	
	public Color getHex() {
		return color;
	}

	public String getName() {
		return colorName;
	}
	
	public boolean isSameColor(String other) {
		return other.equals(colorName);
	}
	
	@Override
	public String toString() {
		return colorName;
	}

}