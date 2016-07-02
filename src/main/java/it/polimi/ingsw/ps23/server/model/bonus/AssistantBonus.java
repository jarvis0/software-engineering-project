package it.polimi.ingsw.ps23.server.model.bonus;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
/**
 * Provides methods to take the specified bonus
 * @author Giuseppe Mascellaro, Mirco Manzoni
 *
 */
public class AssistantBonus extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -372489599150171501L;
	/**
	 * Construct the bonus to be cloned by {@link BonusCache}.
	 * @param name - the name of the bonus@param name
	 */
	public AssistantBonus(String name) {
		super(name);
	}
	
	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) {
		game.getCurrentPlayer().updateAssistants(getValue());
	}

}
