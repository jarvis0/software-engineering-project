package it.polimi.ingsw.ps23.model.actions;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.GroupRegionalCity;

public class ChangePermitsTile  extends QuickAction {

	private static final int ASSISTANTS_COST = -1;
	String regionName;
	int tileIndex;
	
	public ChangePermitsTile(String regionName) {
		this.regionName = regionName;
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		 try {
			game.getCurrentPlayer().updateAssistants(ASSISTANTS_COST);
		} catch (InsufficientResourcesException e) {
			e.printStackTrace();
		}
		 ((GroupRegionalCity) game.getGameMap().getRegion(regionName)).changePermitTiles();
		 turnHandler.useQuickAction();
	}

}
