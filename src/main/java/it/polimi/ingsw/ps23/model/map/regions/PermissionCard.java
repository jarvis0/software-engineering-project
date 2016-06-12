package it.polimi.ingsw.ps23.model.map.regions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.bonus.Bonus;
import it.polimi.ingsw.ps23.bonus.BonusSlot;
import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

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
		print += cities.get(0).getName();
		for(int i = 1; i < cities.size(); i++) {
			print += "/" + cities.get(i).getName();
		}
		return print;
	}
	
}
