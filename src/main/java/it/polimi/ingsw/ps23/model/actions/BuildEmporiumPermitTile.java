package it.polimi.ingsw.ps23.model.actions;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.City;

public class BuildEmporiumPermitTile extends MainAction {

	City buildInThisCity;
	private int chosenCard;
	public BuildEmporiumPermitTile(City city, int chosenCard) {
		this.buildInThisCity = city;
		this.chosenCard = chosenCard;
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		game.getCurrentPlayer().updateEmporiumSet(game, turnHandler, buildInThisCity);
		game.getCurrentPlayer().usePermissionCard(chosenCard);
		turnHandler.useMainAction();
	}



}
