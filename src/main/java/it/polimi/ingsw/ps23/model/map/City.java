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
		
		emporiumList = new ArrayList<Player>();
	}
	
	@Override
	public String toString() {
		return name + " " + color;
	}
	
	public void buildEmporium(Player player) throws AlreadyBuiltHereException { //dovrebbe ritornare il nome della città
		if (emporiumList.contains(player))
			throw new AlreadyBuiltHereException();
		else 
			emporiumList.add(player); //qui dovrebbe far ritornare la città in cui ha costruito al player
	}
	
	public void adiacent() {
		
	}
}