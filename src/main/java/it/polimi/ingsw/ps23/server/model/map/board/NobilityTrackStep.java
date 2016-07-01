package it.polimi.ingsw.ps23.server.model.map.board;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.bonus.BonusSlot;
/**
 * Provides methods to use bonuses on the {@link NobilityTrack}.
 * @author Alessandro Erba
 *
 */
public class NobilityTrackStep implements BonusSlot {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8355589099422337310L;

	private List<Bonus> bonuses;
	/**
	 * Initialize the nobility track step with default bonuses
	 */
	public NobilityTrackStep() {
		bonuses = new ArrayList<>();
	}
	
	void useBonus(Game game, TurnHandler turnHandler) {
		for (Bonus bonus : bonuses) {
			bonus.updateBonus(game, turnHandler);
		}
	}
	
	public List<Bonus> getBonuses() {
		return bonuses;
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
