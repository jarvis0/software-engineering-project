package it.polimi.ingsw.ps23.server.model.actions;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.server.commons.exceptions.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.player.Player;

public class BuildEmporiumPermitTile implements Action {

	private City buildInThisCity;
	private int chosenCard;
	
	private Logger logger;
	
	public BuildEmporiumPermitTile(City city, int chosenCard) {
		this.buildInThisCity = city;
		this.chosenCard = chosenCard;
		logger = Logger.getLogger(this.getClass().getName());
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		Player player = game.getCurrentPlayer();
		player.updateEmporiumSet(game, turnHandler, buildInThisCity);
		try {
			buildInThisCity.buildEmporium(player);
		} catch (AlreadyBuiltHereException e) {
			logger.log(Level.SEVERE, "Cannot build here.", e);
		}
		player.usePermissionCard(chosenCard);
		player.checkEmporiumsGroups(game);
		turnHandler.useMainAction();
	}

}
