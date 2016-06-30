package it.polimi.ingsw.ps23.server.model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;

class BonusTilesSet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8693190914348433544L;
	private List<Bonus> bonuses;
	
	BonusTilesSet() {
		bonuses = new ArrayList<>();
	}
	
	void addTile(Bonus bonus) {
		bonuses.add(bonus);
	}
	
	void useBonus(Game game, TurnHandler turnHandler) {
		for(Bonus bonus : bonuses) {
			bonus.updateBonus(game, turnHandler);
		}
	}
	
}
