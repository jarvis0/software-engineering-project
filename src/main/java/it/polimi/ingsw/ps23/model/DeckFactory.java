package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.Card;

public abstract class DeckFactory {

	private List<Card> cards;
	
	protected DeckFactory() {
		cards = new ArrayList<>();
	}
	
	protected List<Card> getCards() {
		return cards;
	}

}
