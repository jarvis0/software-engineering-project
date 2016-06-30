package it.polimi.ingsw.ps23.server.model.map.regions;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.BonusSlot;
import it.polimi.ingsw.ps23.server.model.map.Card;

public class BusinessPermitTile implements Card, BonusSlot {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3333660744522480459L;
	private List<Bonus> bonuses;
	private List<City> cities;
	
	public BusinessPermitTile() {
		bonuses = new ArrayList<>();
		cities = new ArrayList<>();
	}
	
	@Override
	public void addBonus(Bonus bonus) {
		this.bonuses.add(bonus);
	}
	
	public void addCity(City city) {
		this.cities.add(city);
	}
	
	public boolean containCity(City city) {
		return cities.contains(city);
	}

	public void useBonus(Game game, TurnHandler turnHandler) {
		for (Bonus bonus : bonuses) {
			bonus.updateBonus(game, turnHandler);
		}
	}
	
	public List<Bonus> getBonuses() {
		return bonuses;
	}
	
	public List<City> getCities() {
		return cities;
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
