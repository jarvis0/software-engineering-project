package it.polimi.ingsw.ps23.server.model.map.regions;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.BonusSlot;
import it.polimi.ingsw.ps23.server.model.bonus.NobilityTrackStepBonus;
import it.polimi.ingsw.ps23.server.model.bonus.RealBonus;
/**
 * Provide methods to give specific {@link Bonus} to the player when build in a {@link NormalCity}.
 * @author Giuseppe Mascellaro
 *
 */
public class RewardToken implements BonusSlot {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -549430028050607050L;

	private List<Bonus> bonuses;
	/**
	 * Initialize all the variables to the default values.
	 */
	public RewardToken() {
		bonuses = new ArrayList<>();
	}
	
	public List<Bonus> getBonuses() {
		return bonuses;
	}
	
	@Override
	public void addBonus(Bonus bonus) {
		bonuses.add(bonus);
	}
	
	void useBonus(Game game, TurnHandler turnHandler) {
		for(Bonus bonus : bonuses) {
			((RealBonus)bonus).updateBonus(game, turnHandler);
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
