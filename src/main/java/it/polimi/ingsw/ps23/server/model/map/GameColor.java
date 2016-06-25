package it.polimi.ingsw.ps23.server.model.map;

import java.awt.Color;
import java.io.Serializable;

/**
 * Flyweight pattern implementation for game color.
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public class GameColor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7625811392414498777L;
	private Color color;//TODO maybe useless
	private String colorName;
	
	/**
	 * Creates a new game color identified by a name and a 
	 * hexadecimal format string representing the color.
	 * @param colorName - name of the color
	 * @param colorHex - hexadecimal number for the specified color name.
	 */
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
	
	/**
	 * Checks if two colors are the same.
	 * @param other - color name to be compared with
	 * @return true if the specified color is equals to the parametric
	 * color, false if it's different.
	 */
	public boolean isSameColor(String other) {
		return other.equals(colorName);
	}
	
	@Override
	public String toString() {
		return colorName;
	}

}