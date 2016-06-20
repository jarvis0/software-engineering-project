package it.polimi.ingsw.ps23.server.model.map.regions;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.BonusSlot;
import it.polimi.ingsw.ps23.server.model.bonus.NobilityTrackStepBonus;

public class RewardToken implements BonusSlot {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -549430028050607050L;

	private List<Bonus> bonuses;
	
	public RewardToken() {
		bonuses = new ArrayList<>();
	}
	
	@Override
	public void addBonus(Bonus bonus) {
		bonuses.add(bonus);
	}
	
	void useBonus(Game game, TurnHandler turnHandler) {
		for(Bonus bonus : bonuses) {
			bonus.updateBonus(game, turnHandler);
		}
	}

	boolean hasNobilityTrackBonus() {
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
