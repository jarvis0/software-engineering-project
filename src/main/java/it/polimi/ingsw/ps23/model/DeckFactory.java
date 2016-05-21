package it.polimi.ingsw.ps23.model;

import java.util.List;

import it.polimi.ingsw.ps23.model.map.Deck;

public abstract class DeckFactory {

	public abstract Deck makeDeck(List<String[]> rawCards);

}
