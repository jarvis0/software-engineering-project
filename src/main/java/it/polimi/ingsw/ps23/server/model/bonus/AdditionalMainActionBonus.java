package it.polimi.ingsw.ps23.server.model.bonus;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
/**
 * Provides methods to take the specified bonus
 * @author Giuseppe Mascellaro, Mirco Manzoni
 *
 */
public class AdditionalMainActionBonus extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7637353928174378873L;
	/**
	 * Construct the bonus to be cloned by {@link BonusCache}.
	 * @param name - the name of the bonus
	 */
	public AdditionalMainActionBonus(String name) {
		super(name);
	}

	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) {
		turnHandler.addMainAction();		
	}

	@Override
	public boolean isNull() {
		return false;
	}
	
}
