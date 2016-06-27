package it.polimi.ingsw.ps23.server.model.bonus;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class CoinBonus extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8863360161746536864L;

	public CoinBonus(String name) {
		super(name);
	}

	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) {
		game.getCurrentPlayer().updateCoins(getValue());
	}

}
