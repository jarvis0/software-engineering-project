package it.polimi.ingsw.ps23.server.model.map.regions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
import it.polimi.ingsw.ps23.server.model.player.Player;

public class City implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4628045111105663222L;
	private String name;
	private GameColor color;
	private List<Player> emporiumsList;
	
	protected City(String name, GameColor color) {
		this.name = name;
		this.color = color;
		emporiumsList = new ArrayList<>();
	}

	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color.getName();
	}
	
	public int buildEmporium(Player player) throws AlreadyBuiltHereException { 
		int assitantsCost = 0;
		if(!emporiumsList.isEmpty()) {
			assitantsCost =	- emporiumsList.size();
		}
		emporiumsList.add(player); 
		return assitantsCost;
	}

	List<String> getEmporiumsPlayersList() {
		List<String> emporiumsPlayersList = new ArrayList<>();
		for(Player emporium : emporiumsList) {
			emporiumsPlayersList.add(emporium.getName());
		}
		return emporiumsPlayersList;
	}
	
}