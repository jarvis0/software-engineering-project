package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.map.Card;

public abstract class DeckFactory {

	private ArrayList<Card> cards;
	
	protected DeckFactory() {
		cards = new ArrayList<>();
	}
	
	protected ArrayList<Card> getCards() {
		return cards;
	}
	
}
