package it.polimi.ingsw.ps23.model.map;

import it.polimi.ingsw.ps23.model.GameColor;

public class CapitalCity extends City {
	
	public CapitalCity(String name, GameColor color){
			super(name, color);
	}

	@Override
	public String toString() {
		return getName() + " " + getColor() + "\n";
	}
	
}
