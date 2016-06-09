package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.GameColor;
import it.polimi.ingsw.ps23.model.Player;

public abstract class City {
	
	private static final int EMPTY = 0;
	private String name;
	private GameColor color;
	private List<Player> emporiumList;
	
	protected City(String name, GameColor color) {
		this.name = name;
		this.color = color;
		emporiumList = new ArrayList<>();
	}

	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color.toString();
	}
	
	public int buildEmporium(Player player) throws AlreadyBuiltHereException { 
		int assitantsCost = 0;	
		if(emporiumList.size() != EMPTY){
			assitantsCost =	-emporiumList.size();
		}
		
		emporiumList.add(player); 
		return assitantsCost;
	}

	public List<Player> getEmporiums() {
		return emporiumList;
	}

}