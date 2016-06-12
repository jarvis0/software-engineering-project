package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.bonus.Bonus;
import it.polimi.ingsw.ps23.model.bonus.NobilityTrackStepBonus;

public class RewardToken implements BonusSlot {
	
	private List<Bonus> bonuses;
	
	private Logger logger;
	
	public RewardToken() {
		bonuses = new ArrayList<>();
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	@Override
	public void addBonus(Bonus bonus) {
		bonuses.add(bonus);
	}
	
	public void useBonus(Game game, TurnHandler turnHandler) {
		for(Bonus bonus : bonuses) {
			try {
				bonus.updateBonus(game, turnHandler);
			} catch (InsufficientResourcesException e) {
				logger.log(Level.SEVERE, "Insufficient current player resources.", e);
				//TODO serve questa eccezione?
			}
		}
	}

	public boolean hasNobilityTrackBonus() {
		for(Bonus bonus : bonuses) {
			if(bonus instanceof NobilityTrackStepBonus) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return bonuses.toString();
	}

}
