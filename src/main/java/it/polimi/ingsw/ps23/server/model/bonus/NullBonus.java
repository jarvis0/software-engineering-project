package it.polimi.ingsw.ps23.server.model.bonus;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class NullBonus extends Bonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4539549879925411110L;

	public NullBonus(String name) {
		super(name);
	}

	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) {
		return;
	}
	
}
