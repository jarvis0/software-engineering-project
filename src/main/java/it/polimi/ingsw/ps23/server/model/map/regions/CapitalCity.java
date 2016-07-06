package it.polimi.ingsw.ps23.server.model.map.regions;

import it.polimi.ingsw.ps23.server.model.map.GameColor;
/**
 * Abstraction of city to recognize thi type of city from other types.
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public class CapitalCity extends City {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -409817877984277269L;
	/**
	 * Construct a capital city with its name and color
	 * @param name - the name of the city
	 * @param color - the color of the city
	 */
	public CapitalCity(String name, GameColor color){
		super(name, color, true);
	}

	@Override
	public String toString() {
		return getName() + " " + getColor() + " " + getEmporiumsPlayersList();
	}
	
}
