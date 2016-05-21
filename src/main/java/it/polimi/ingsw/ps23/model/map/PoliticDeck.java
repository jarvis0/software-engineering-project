package it.polimi.ingsw.ps23.model.map;

import java.util.List;

public class PoliticDeck extends Deck {

	public PoliticDeck(List<Card> cards) {
		super(cards);
	}
	
	@Override
	public String toString() {
		return this.getDeck().toString();
	}
	
}
