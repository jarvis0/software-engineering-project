package it.polimi.ingsw.ps23.model.actions;

import java.util.List;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.PoliticHandDeck;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.Region;

public class AcquireBusinessPermitTile extends MainAction {

	private List<String> removedPoliticCards;
	private Region chosenRegion;
	private int chosenPermissionCard;
	
	public AcquireBusinessPermitTile(List<String> removedPoliticCards, Region chosenRegion, int chosenPermissionCard) {
		this.removedPoliticCards = removedPoliticCards;
		this.chosenRegion = chosenRegion;
		this.chosenPermissionCard = chosenPermissionCard;
	}
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		int cost = ((PoliticHandDeck) game.getCurrentPlayer().getPoliticHandDeck()).removeCards(removedPoliticCards);
		try {
			game.getCurrentPlayer().updateCoins(cost);
		} catch (InsufficientResourcesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		game.getCurrentPlayer().pickPermitCard(chosenRegion, chosenPermissionCard, turnHandler);
		turnHandler.useMainAction();
	}
}
