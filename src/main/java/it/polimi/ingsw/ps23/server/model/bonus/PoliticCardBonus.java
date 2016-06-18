package it.polimi.ingsw.ps23.server.model.bonus;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class PoliticCardBonus extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6343448965365853724L;

	public PoliticCardBonus(String name) {
		super(name);
	}

	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) throws InsufficientResourcesException {
		game.getCurrentPlayer().pickCard(game.getPoliticDeck(), getValue()); 
	}
	
}
