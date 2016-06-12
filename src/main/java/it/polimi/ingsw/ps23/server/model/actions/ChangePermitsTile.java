package it.polimi.ingsw.ps23.server.model.actions;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.regions.GroupRegionalCity;

public class ChangePermitsTile implements Action {

	private static final int ASSISTANTS_COST = -1;
	private String regionName;
	
	private Logger logger;
	
	public ChangePermitsTile(String regionName) {
		this.regionName = regionName;
		logger = Logger.getLogger(this.getClass().getName());
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		 try {
			game.getCurrentPlayer().updateAssistants(ASSISTANTS_COST);
		} catch (InsufficientResourcesException e) {
			logger.log(Level.SEVERE, "Insufficient current player assistants.", e);
		}
		((GroupRegionalCity) game.getGameMap().getRegion(regionName)).changePermitTiles();
		turnHandler.useQuickAction();
	}

}
