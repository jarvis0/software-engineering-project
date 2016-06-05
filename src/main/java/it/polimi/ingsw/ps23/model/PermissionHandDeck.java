package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.Card;

public class PermissionHandDeck extends HandDeck {
	
	public PermissionHandDeck(List<Card> returnCards) {
		super();
		addCards(returnCards);
	}
	
	
	public void addCards(List<Card> permissionHandCards) {
		for (Card permissionHandCard: permissionHandCards) {
			addCard(permissionHandCard);
		}
	}
	
	public HandDeck getAvaiblePermissionCards() {
		List<Card> returnCards = new ArrayList<>();
		returnCards.addAll(getCards());
		return new PermissionHandDeck(returnCards);
	}
	
}
