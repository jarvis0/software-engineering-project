package it.polimi.ingsw.ps23.server.model.map.regions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
import it.polimi.ingsw.ps23.server.model.player.Player;
/**
 * Provide methods to manage a city
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
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
		return color.toString();
	}
	
	public boolean isCapital() {
		return capital;
	}
	/**
	 * Adds a {@link Player} references to the list of players that have built in this city.
	 * @param player - the current player
	 * @return the assistant costs of this action.
	 * @throws AlreadyBuiltHereException if {@link Player} have already an emporium in this	 city
	 * @throws InsufficientResourcesException if {@link Player} haven't got enough assistant to complete this action
	 */
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
	/**
	 * Finds the emporiums players list related to the specified city.
	 * @return the emporiums players list.
	 */
	public List<String> getEmporiumsPlayersList() {
		List<String> emporiumsPlayersList = new ArrayList<>();
		for(Player emporium : emporiumsList) {
			emporiumsPlayersList.add(emporium.getName());
		}
		return emporiumsPlayersList;
	}
	
}