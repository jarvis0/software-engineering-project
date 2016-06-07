package it.polimi.ingsw.ps23.model.actions;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.City;

public class BuildEmporiumPermitTile extends MainAction {

	City buildInThisCity;
	private int chosenCard;
	private int initialNobilityTrackPoints;
	private int finalNobilityTrackPoints;
	
	public BuildEmporiumPermitTile(City city, int chosenCard, int initialNobilityTrackPoints) {
		this.buildInThisCity = city;
		this.chosenCard = chosenCard;
		this.initialNobilityTrackPoints = initialNobilityTrackPoints;
		
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		game.getCurrentPlayer().updateEmporiumSet(buildInThisCity, game.getGameMap().getCitiesGraph());
		game.getCurrentPlayer().usePermissionCard(chosenCard);	
		turnHandler.useMainAction();
	}



}
