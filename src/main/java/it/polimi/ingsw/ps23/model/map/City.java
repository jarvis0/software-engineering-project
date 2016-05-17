package it.polimi.ingsw.ps23.model.map;

import java.awt.Color;
import java.util.ArrayList;
import it.polimi.ingsw.ps23.model.Player;

public abstract class City {
	private String name;
	private Color color;
	private ArrayList<Player> emporiumList;
	
	public City(String name, Color color){
		this.name = name;
		this.color = color;
		emporiumList = new ArrayList<Player>();
	}
	
	public void buildEmporium(Player player) throws AlreadyBuiltHereException{ //dovrebbe ritornare il nome della città
		if (emporiumList.contains(player))
			throw new AlreadyBuiltHereException();
		else 
			emporiumList.add(player); //qui dovrebbe far ritornare la città in cui ha costruito al player
	}
	
	public void adiacent() {
		
	}
}