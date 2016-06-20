package it.polimi.ingsw.ps23.server.model.actions;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.server.commons.exceptions.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.player.Player;

public class BuildEmporiumPermitTile implements Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2547456385651538388L;
	private City buildInThisCity;
	private int chosenCard;

	public BuildEmporiumPermitTile(City city, int chosenCard) {
		this.buildInThisCity = city;
		this.chosenCard = chosenCard;
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) throws InsufficientResourcesException, AlreadyBuiltHereException {
		Player player = game.getCurrentPlayer();
		buildInThisCity.buildEmporium(player);
		player.updateEmporiumSet(game, turnHandler, buildInThisCity);		
		player.usePermissionCard(chosenCard);
		player.checkEmporiumsGroups(game);
		turnHandler.useMainAction();
	}

}
