package it.polimi.ingsw.ps23.model.map;

import java.util.Collections;
import java.util.List;

public class Deck {
	
	private List<Card> cards;
	
	public Deck(List<Card> cards) {
		Collections.shuffle(cards);
		this.cards = cards;
	}
	
	public List<Card> getDeck() {
		return cards;
	}
}