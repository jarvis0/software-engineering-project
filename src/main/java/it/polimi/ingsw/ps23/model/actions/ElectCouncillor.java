package it.polimi.ingsw.ps23.model.actions;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.Council;

public class ElectCouncillor extends MainAction {
	
	private static final int EARNED_COINS = 4;
	
	private String councillor;
	private Council council;
		
	public ElectCouncillor(String councillor, Council council) {
		this.councillor = councillor;
		this.council = council;
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		game.getFreeCouncillors().electCouncillor(councillor, council);
		try {
			game.getCurrentPlayer().updateCoins(EARNED_COINS);
		} catch (InsufficientResourcesException e) {
			e.printStackTrace();
		}
		turnHandler.useMainAction();
	}
}
