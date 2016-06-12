package it.polimi.ingsw.ps23.server.model.map.regions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.BonusSlot;
import it.polimi.ingsw.ps23.server.model.map.Card;

public class PermissionCard implements Card, BonusSlot {

	private List<Bonus> bonuses;
	private List<City> cities;
	
	private Logger logger;
	
	public PermissionCard() {
		bonuses = new ArrayList<>();
		cities = new ArrayList<>();
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	@Override
	public void addBonus(Bonus bonus) {
		this.bonuses.add(bonus);
	}
	
	public void addCity(City city) {
		this.cities.add(city);
	}

	public void useBonus(Game game, TurnHandler turnHandler) {
		for (Bonus bonus : bonuses) {
			try {
				bonus.updateBonus(game, turnHandler);
			} catch (InsufficientResourcesException e) {
				logger.log(Level.SEVERE, "Insufficient current player resources.", e);
				//TODO serve questa eccezione?
			}
		}
	}
	
	@Override
	public String toString() {
		String print = bonuses.toString() + " ~ ";
		if(!cities.isEmpty()) {
			print += cities.get(0).getName();
		}
		StringBuilder loopPrint = new StringBuilder();
		for(int i = 1; i < cities.size(); i++) {
			loopPrint.append("/" + cities.get(i).getName());
		}
		return print + loopPrint;
	}
	
}
