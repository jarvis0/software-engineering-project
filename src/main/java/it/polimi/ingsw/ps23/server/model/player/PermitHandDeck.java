package it.polimi.ingsw.ps23.server.model.player;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.map.Card;
/**
 * Provides methods to manage the {@link BusinessPermitTile} of a {@link Player}.
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public class PermitHandDeck extends HandDeck {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8754158271215617734L;

	PermitHandDeck() {
		super();
	}
	
	void addCards(List<Card> permissionHandCards) {
		for (Card permissionHandCard: permissionHandCards) {
			addCard(permissionHandCard);
		}
	}
	
	/**
	 * @return all current player's available hand permit tiles.
	 */
	public HandDeck getAvaiblePermissionCards() {
		List<Card> returnCards = new ArrayList<>();
		returnCards.addAll(getCards());
		PermitHandDeck returnHandDeck = new PermitHandDeck();
		returnHandDeck.addCards(returnCards);
		return returnHandDeck;
	}
	
}
