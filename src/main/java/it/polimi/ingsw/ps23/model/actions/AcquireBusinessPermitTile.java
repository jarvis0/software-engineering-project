package it.polimi.ingsw.ps23.model.actions;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.PermissionCard;

public class AcquireBusinessPermitTile extends MainAction {

	//altre cose
	private Card permissionCard;
	
	public AcquireBusinessPermitTile() {
		//
	}
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		// TODO Auto-generated method stub
		((PermissionCard)permissionCard).useBonus(game.getCurrentPlayer(), turnHandler);
		turnHandler.useMainAction();
	}

}
