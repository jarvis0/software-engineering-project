package it.polimi.ingsw.ps23.server.model.player;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.map.Card;

public class PermissionHandDeck extends HandDeck {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8754158271215617734L;

	PermissionHandDeck() {
		super();
	}
	
	void addCards(List<Card> permissionHandCards) {
		for (Card permissionHandCard: permissionHandCards) {
			addCard(permissionHandCard);
		}
	}
	
	public HandDeck getAvaiblePermissionCards() {
		List<Card> returnCards = new ArrayList<>();
		returnCards.addAll(getCards());
		PermissionHandDeck returnHandDeck = new PermissionHandDeck();
		returnHandDeck.addCards(returnCards);
		return returnHandDeck;
	}
	
}
