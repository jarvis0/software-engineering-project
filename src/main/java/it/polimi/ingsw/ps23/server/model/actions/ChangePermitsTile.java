package it.polimi.ingsw.ps23.server.model.actions;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;

public class ChangePermitsTile implements Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4131871312323174768L;
	private static final int ASSISTANTS_COST = -1;
	private String regionName;
	
	public ChangePermitsTile(String regionName) {
		this.regionName = regionName;
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		 try {
			game.getCurrentPlayer().updateAssistants(ASSISTANTS_COST);
		} catch (InsufficientResourcesException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Insufficient current player assistants.", e);
		}
		((GroupRegionalCity) game.getGameMap().getRegion(regionName)).changePermitTiles();
		turnHandler.useQuickAction();
	}

}
