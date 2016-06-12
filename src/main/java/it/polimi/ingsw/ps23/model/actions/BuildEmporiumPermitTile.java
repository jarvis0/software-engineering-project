package it.polimi.ingsw.ps23.model.actions;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.model.map.regions.City;

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
