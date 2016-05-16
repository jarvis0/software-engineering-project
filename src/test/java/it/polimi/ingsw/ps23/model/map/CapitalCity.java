package it.polimi.ingsw.ps23.model.map;

import java.awt.Color;
import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.Player;

public class CapitalCity extends City {
	
	public CapitalCity(String name, Color color){
		this.name = name;
		this.color = color;
		emporiumList = new ArrayList<Player>();
	}
	

}
