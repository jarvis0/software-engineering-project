package it.polimi.ingsw.ps23.server.model.bonus;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public class AdditionalMainActionBonus extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7637353928174378873L;

	public AdditionalMainActionBonus(String name) {
		super(name);
	}

	@Override
	public void updateBonus(Game game, TurnHandler turnHandler) throws InsufficientResourcesException {
		turnHandler.addMainAction();		
	}
	
}
