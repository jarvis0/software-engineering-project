package it.polimi.ingsw.ps23.server.model.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1360173791794831347L;

	private static final int TOP_CARD = 0;
	
	private List<Card> cards;
	
	public Deck(List<Card> cards) {
		Collections.shuffle(cards);
		this.cards = cards;
	}

	public List<Card> getDeck() {
		return cards;
	}
	
	public List<Card> pickCards(int cardsNumber) {
		List<Card> pickedCards = new ArrayList<>();
		for(int i = 0; i < cardsNumber; i++) {
			pickedCards.add(cards.remove(cards.size() - 1));
		}
		return pickedCards;
	}

	public Card pickCard() {
		return cards.remove(TOP_CARD);
	}
	
	@Override
	public String toString() {
		return getDeck().toString();
	}
	
}
