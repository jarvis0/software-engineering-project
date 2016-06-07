package it.polimi.ingsw.ps23.model.bonus;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;

public class CoinBonus extends Bonus {

	public CoinBonus(String name) {
		super(name);
	}

	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) throws InsufficientResourcesException {
		game.getCurrentPlayer().updateCoins(getValue());
	}

}
