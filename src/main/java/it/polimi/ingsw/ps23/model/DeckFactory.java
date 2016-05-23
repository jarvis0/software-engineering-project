package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.Deck;

public abstract class DeckFactory {

	private ArrayList<Card> cards;
	
	protected DeckFactory() {
		cards = new ArrayList<>();
	}
	
	public abstract Deck makeDeck(List<String[]> rawCards);

	protected ArrayList<Card> getCards() {
		return cards;
	}
	
}
