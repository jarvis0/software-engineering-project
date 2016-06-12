package it.polimi.ingsw.ps23.server.model.player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;

class BonusTile {
	
	private List<Bonus> bonuses;
	
	private Logger logger;
	
	BonusTile() {
		bonuses = new ArrayList<>();
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	void addTile(Bonus bonus) {
		bonuses.add(bonus);
	}
	
	void useBonus(Game game, TurnHandler turnHandler) {
		for(Bonus bonus : bonuses) {
			try {
				bonus.updateBonus(game, turnHandler);
			} catch (InsufficientResourcesException e) {
				//TODO che eccezione lancia qui?
				logger.log(Level.SEVERE, "Cannot initialize the server connection socket.", e);
			}
		}
	}
	
}
