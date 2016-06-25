package it.polimi.ingsw.ps23.server.model.map.regions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
import it.polimi.ingsw.ps23.server.model.player.Player;

public class City implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4628045111105663222L;
	private String name;
	private GameColor color;
	private boolean capital;
	private List<Player> emporiumsList;
	
	protected City(String name, GameColor color, boolean capital) {
		this.name = name;
		this.color = color;
		this.capital = capital;
		emporiumsList = new ArrayList<>();
	}

	public String getName() {
		return name;
	}
	
	public String getColor() {
		return color.getName();
	}
	
	public boolean isCapital() {
		return capital;
	}
	
	public int buildEmporium(Player player) throws AlreadyBuiltHereException, InsufficientResourcesException { 
		int assitantsCost = 0;
		if(!emporiumsList.isEmpty()) {
			assitantsCost =	- emporiumsList.size();
		}
		if(Math.abs(assitantsCost) > player.getAssistants()) {
			throw new InsufficientResourcesException();
		}
		if(emporiumsList.contains(player)) {
			throw new AlreadyBuiltHereException();
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