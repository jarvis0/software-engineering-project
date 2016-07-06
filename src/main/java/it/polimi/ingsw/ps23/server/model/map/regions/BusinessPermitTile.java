package it.polimi.ingsw.ps23.server.model.map.regions;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.BonusSlot;
import it.polimi.ingsw.ps23.server.model.bonus.RealBonus;
import it.polimi.ingsw.ps23.server.model.map.Card;
/**
 * Provide methods to manage the card and add bonuses to players. 
 * @author Alessandro Erba, Giuseppe Mascellaro, Mirco Manzoni.
 *
 */
public class BusinessPermitTile implements Card, BonusSlot {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3333660744522480459L;
	private List<Bonus> bonuses;
	private List<City> cities;
	/**
	 * Initialize all variables to the default values.
	 */
	public BusinessPermitTile() {
		bonuses = new ArrayList<>();
		cities = new ArrayList<>();
	}
	
	public List<Bonus> getBonuses() {
		return bonuses;
	}
	
	public List<City> getCities() {
		return cities;
	}
	
	@Override
	public void addBonus(Bonus bonus) {
		this.bonuses.add(bonus);
	}
	/**
	 * Adds a city to the pool of the card
	 * @param city - city to add
	 */
	public void addCity(City city) {
		this.cities.add(city);
	}
	/**
	 * Calculate if the chosen city is contained in card.
	 * @param city - the chosen city
	 * @return true if contains, false if not.
	 */
	public boolean containCity(City city) {
		return cities.contains(city);
	}
	/**
	 * Applies bonuses to the current {@link Player} 
	 * @param game - current game to apply bonuses
	 * @param turnHandler - current turn handler to apply bonus
	 */
	public void useBonus(Game game, TurnHandler turnHandler) {
		for (Bonus bonus : bonuses) {
			((RealBonus)bonus).updateBonus(game, turnHandler);
		}
	}

	@Override
	public String toString() {
		String print = bonuses.toString() + " ~ ";
		if(!cities.isEmpty()) {
			print += Character.toString(cities.get(0).getName().charAt(0));
		}
		StringBuilder loopPrint = new StringBuilder();
		for(int i = 1; i < cities.size(); i++) {
			loopPrint.append("/" + cities.get(i).getName().charAt(0));
		}
		return print + loopPrint;
	}
	
}
