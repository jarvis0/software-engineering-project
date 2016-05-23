package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.GameColor;
import it.polimi.ingsw.ps23.model.Player;

public abstract class City {
	
	private GameColor color;
	private ArrayList<Player> emporiums;
	
	protected City(GameColor color) {
		this.color = color;
	}

	public String getColor() {
		return color.toString();
	}
	
	public abstract void buildEmporium(Player player);
	
	public void adiacent() {
		
	}
}