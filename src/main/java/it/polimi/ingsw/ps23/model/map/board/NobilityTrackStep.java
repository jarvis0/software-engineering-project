package it.polimi.ingsw.ps23.model.map.board;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.bonus.BonusSlot;

public class NobilityTrackStep implements BonusSlot {

	private List<Bonus> bonuses;
	
	private Logger logger;
	
	public NobilityTrackStep() {
		bonuses = new ArrayList<>();
		logger = Logger.getLogger(this.getClass().getName());
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
	public void addBonus(Bonus bonus) {
		bonuses.add(bonus);		
	}
		
	@Override
	public String toString() {
		return bonuses.toString();
	}

}
