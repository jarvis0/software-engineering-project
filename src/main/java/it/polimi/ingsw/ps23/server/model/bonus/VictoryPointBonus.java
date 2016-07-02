package it.polimi.ingsw.ps23.server.model.bonus;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
/**
 * Provides methods to take the specified bonus
 * @author Giuseppe Mascellaro, Mirco Manzoni
 *
 */
public class VictoryPointBonus extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7580304211077512707L;
	/**
	 * Construct the bonus to be cloned by {@link BonusCache}.
	 * @param name - the name of the bonus
	 */
	public VictoryPointBonus(String name) {
		super(name);
	}
	
	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) {
		game.getCurrentPlayer().updateVictoryPoints(getValue());
	}
	
}
