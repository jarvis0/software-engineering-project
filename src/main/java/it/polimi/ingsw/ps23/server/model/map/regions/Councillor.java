package it.polimi.ingsw.ps23.server.model.map.regions;

import java.io.Serializable;

import it.polimi.ingsw.ps23.server.model.map.GameColor;
/**
 * Provide methods to recognize the color of the councillor.
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public class Councillor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2120158006011256971L;
	private GameColor color;
	/**
	 * Create the councillor with a specified color
	 * @param color - the color of the councillor
	 */
	public Councillor(GameColor color) {
		this.color = color;
	}
	
	public GameColor getColor() {
		return color;
	}
	
	@Override
	public String toString() {
		return color.toString();
	}

}
