package it.polimi.ingsw.ps23.server.model.bonus;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
/**
 * Provides methods to take the specified bonus
 * @author Giuseppe Mascellaro, Mirco Manzoni
 *
 */
public class PoliticCardBonus extends RealBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6343448965365853724L;
	/**
	 * Construct the bonus to be cloned by {@link BonusCache}.
	 * @param name - the name of the bonus
	 */
	public PoliticCardBonus(String name) {
		super(name);
	}

	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) {
		game.getCurrentPlayer().pickCard(game.getPoliticDeck(), getValue()); 
	}

	@Override
	public boolean isNull() {
		return false;
	}
	
}
