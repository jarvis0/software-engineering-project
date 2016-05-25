package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.Card;

public abstract class HandDeck {
	
	private List<Card> cards;

	protected HandDeck() {
		cards = new ArrayList<Card>();
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
	@Override
	public String toString() {
		return cards.toString();
	}
}
