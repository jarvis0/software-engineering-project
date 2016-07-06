package it.polimi.ingsw.ps23.server.model.actions;

import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidRegionException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;

/**
 * Provides methods to perform the specified game action if
 * the action is in a valid format.
 * @author Alessandro Erba & Mirco Manzoni
 *
 */
public class AcquireBusinessPermitTile extends Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 333874053997816119L;
	private List<String> removedPoliticCards;
	private String chosenRegion;
	private int chosenPermissionCard;
	
	/**
	 * Saves all specified action parameters in order to perform the game action.
	 * @param removedPoliticCards - politic cards to be used for satisfy a regional council
	 * @param chosenRegion - the regional council to be satisfied
	 * @param chosenPermissionCard - 1 for left permission card, 2 for right permission card
	 */
	public AcquireBusinessPermitTile(List<String> removedPoliticCards, String chosenRegion, int chosenPermissionCard) {
		this.removedPoliticCards = removedPoliticCards;
		this.chosenRegion = chosenRegion;
		this.chosenPermissionCard = chosenPermissionCard;
	}
	
	private void checkAction(Game game) throws InvalidRegionException {
		if(game.getGameMap().getRegionMap().get(chosenRegion) == null) {
			throw new InvalidRegionException();
		}
	}
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) throws InvalidCardException, InsufficientResourcesException, InvalidRegionException {
		checkAction(game);
		int cost = ((PoliticHandDeck) game.getCurrentPlayer().getPoliticHandDeck()).checkCost(removedPoliticCards);
		if(Math.abs(cost) > game.getCurrentPlayer().getCoins()) {
			throw new InsufficientResourcesException();
		}
		if(chosenPermissionCard < 0 || chosenPermissionCard > 1) {
			throw new InvalidCardException();
		}
		Player player = game.getCurrentPlayer();
		((PoliticHandDeck) player.getPoliticHandDeck()).removeCards(removedPoliticCards);
		player.updateCoins(cost);
		player.pickPermitCard(game, turnHandler, game.getGameMap().getRegionMap().get(chosenRegion), chosenPermissionCard);
		turnHandler.useMainAction();
		setActionReport("Player " + player.getName() + " acquired a business permit tile (" + player.getPermitHandDeck().getCardInPosition(player.getPermitHandDeck().getHandSize() - 1) + ") satisfying the " + chosenRegion + "'s council with these cards: " + removedPoliticCards);
	}
		
}
