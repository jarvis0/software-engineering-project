package it.polimi.ingsw.ps23.server.model.actions;

import it.polimi.ingsw.ps23.server.commons.exceptions.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.PermissionCard;
import it.polimi.ingsw.ps23.server.model.player.Player;

public class BuildEmporiumPermitTile implements Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2547456385651538388L;
	private String buildInThisCity;
	private int chosenCard;

	public BuildEmporiumPermitTile(String city, int chosenCard) {
		this.buildInThisCity = city;
		this.chosenCard = chosenCard;
	}
	
	private void checkAction(Game game) throws InvalidCityException, InvalidCardException {
		City selectedCity = game.getGameMap().getCitiesMap().get(buildInThisCity);
		if(selectedCity == null || !((PermissionCard) game.getCurrentPlayer().getPermissionHandDeck().getCardInPosition(chosenCard)).containCity(selectedCity)) {
			throw new InvalidCityException();
		}
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) throws InsufficientResourcesException, AlreadyBuiltHereException, InvalidCityException, InvalidCardException {
		checkAction(game);
		City selectedCity = game.getGameMap().getCitiesMap().get(buildInThisCity);
		Player player = game.getCurrentPlayer();
		selectedCity.buildEmporium(player);
		player.updateEmporiumSet(game, turnHandler, selectedCity);		
		player.usePermissionCard(chosenCard);
		player.checkEmporiumsGroups(game);
		turnHandler.useMainAction();
	}

}
