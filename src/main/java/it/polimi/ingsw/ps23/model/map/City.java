package it.polimi.ingsw.ps23.model.map;

import java.util.List;

import it.polimi.ingsw.ps23.model.GameColor;
import it.polimi.ingsw.ps23.model.Player;

public abstract class City {
	
	private String name;
	private GameColor color;
	private List<Player> emporiumList;
	
	protected City(String name, GameColor color) {
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color.toString();
	}
	
	public void buildEmporium(Player player) throws AlreadyBuiltHereException { 
		if (emporiumList.contains(player))
			throw new AlreadyBuiltHereException();
		else 
			emporiumList.add(player); 
	}

	public List<String> getEmporiums() {
		return emporiumList;
	}
}