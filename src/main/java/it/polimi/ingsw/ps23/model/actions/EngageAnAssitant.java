package it.polimi.ingsw.ps23.model.actions;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;

public class EngageAnAssitant extends QuickAction {

	private static final int COINS_COST = -3;
	private static final int EARNED_ASSITANTS = 1;
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
	
		try {
			game.getCurrentPlayer().updateCoins(COINS_COST);
			game.getCurrentPlayer().updateAssistants(EARNED_ASSITANTS);
		} catch (InsufficientResourcesException e) {
			e.printStackTrace();
		}
		turnHandler.useQuickAction();
	
	}
	

}
