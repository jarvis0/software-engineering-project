package it.polimi.ingsw.ps23.server.model.map.regions;

import java.io.Serializable;

import it.polimi.ingsw.ps23.server.model.map.GameColor;

public class Councillor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2120158006011256971L;
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
