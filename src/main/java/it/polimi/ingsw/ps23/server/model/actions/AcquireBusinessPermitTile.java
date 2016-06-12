package it.polimi.ingsw.ps23.server.model.actions;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;

public class AcquireBusinessPermitTile implements Action {

	private List<String> removedPoliticCards;
	private Region chosenRegion;
	private int chosenPermissionCard;
	
	private Logger logger;
	
	public AcquireBusinessPermitTile(List<String> removedPoliticCards, Region chosenRegion, int chosenPermissionCard) {
		this.removedPoliticCards = removedPoliticCards;
		this.chosenRegion = chosenRegion;
		this.chosenPermissionCard = chosenPermissionCard;
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		int cost = ((PoliticHandDeck) game.getCurrentPlayer().getPoliticHandDeck()).removeCards(removedPoliticCards);
		try {
			game.getCurrentPlayer().updateCoins(cost);
		} catch (InsufficientResourcesException e) {
			logger.log(Level.SEVERE, "Insufficient current player coins.", e);
		}
		game.getCurrentPlayer().pickPermitCard(game, turnHandler, chosenRegion, chosenPermissionCard);
		turnHandler.useMainAction();
	}
	
}
