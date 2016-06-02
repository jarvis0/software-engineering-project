package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	
	private List<Card> cards;
	
	public Deck(List<Card> cards) {
		Collections.shuffle(cards);
		this.cards = cards;
	}
	
	@Override
	public String toString() {
		return this.getDeck().toString();
	}
	
	public List<Card> getDeck() {
		return cards;
	}
	
	public List<Card> pickCards(int cardsNumber) {
		List<Card> pickedCards = new ArrayList<>();
		for (int i = 0; i < cardsNumber; i++) {
			pickedCards.add(cards.remove(cards.size() - 1));
		}
		return pickedCards;
	}

	public Card pickCard() {
		return cards.remove(0);
	}
	
}
