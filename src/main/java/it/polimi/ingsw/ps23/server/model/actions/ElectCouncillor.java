package it.polimi.ingsw.ps23.server.model.actions;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.regions.Council;

public class ElectCouncillor implements Action {
	
	private static final int EARNED_COINS = 4;
	
	private String councillor;
	private Council council;
	
	private Logger logger;
		
	public ElectCouncillor(String councillor, Council council) {
		this.councillor = councillor;
		this.council = council;
		logger = Logger.getLogger(this.getClass().getName());
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		game.getFreeCouncillors().electCouncillor(councillor, council);
		try {
			game.getCurrentPlayer().updateCoins(EARNED_COINS);
		} catch (InsufficientResourcesException e) {
			//TODO qua in realtà non dovrebbe lanciar l'eccezione
			logger.log(Level.SEVERE, "Insufficient current player coins.", e);
		}
		turnHandler.useMainAction();
	}
}
