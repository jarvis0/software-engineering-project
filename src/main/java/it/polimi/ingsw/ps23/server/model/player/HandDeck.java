package it.polimi.ingsw.ps23.server.model.player;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.map.Card;

public abstract class HandDeck {
	
	private List<Card> cards;

	protected HandDeck() {
		cards = new ArrayList<>();
	}
	
	public void addCard(Card card) {
		cards.add(card);
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public int getHandSize() {
		return cards.size();
	}
	
	@Override
	public String toString() {
		return cards.toString();
	}

	public void removeCard(Card card) {
		 cards.remove(card);
	}

	public Card getAndRemove(int chosenCard) {
		Card removedCard = cards.get(chosenCard);
		removeCard(removedCard);
		return removedCard;
	}
	
	public Card getCardInPosition(int index) {
		return cards.get(index);
	}

}
