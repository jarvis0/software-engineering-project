package it.polimi.ingsw.ps23.server.model.bonus;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
/**
 * Provides methods to give bonus to the current {@link Player}.
 * @author Mirco Manzoni
 *
 */
public abstract class RealBonus extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1314323560159334509L;
	private int value;
	
	RealBonus(String name) {
		super(name);
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * The current {@link Player} take the bonus
	 * @param game - to update player's parameters
	 * @param turnHandler - to update availability of actions or superbonuses
	 */
	public abstract void updateBonus(Game game, TurnHandler turnHandler);
	
	@Override
	public String toString() {
		return getName() + " " + value;
	}
}
