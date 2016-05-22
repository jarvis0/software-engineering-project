package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import it.polimi.ingsw.ps23.model.GameColor;
import it.polimi.ingsw.ps23.model.Player;

public abstract class City {
	private String name;
	private GameColor color;
	private ArrayList<Player> emporiumList;
	
	public City(String name, GameColor color) {
		this.name = name;
		this.color = color;
		emporiumList = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return name + " " + color;
	}
	
	public abstract void buildEmporium(Player player);
	
	public void adiacent() {
		
	}
}