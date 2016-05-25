package it.polimi.ingsw.ps23.model;

import java.util.List;

import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.PoliticCard;
import it.polimi.ingsw.ps23.model.map.PoliticDeck;

public class PoliticDeckFactory extends DeckFactory {
	
	public PoliticDeckFactory() {
		super();
	}
	
	public Deck makeDeck(List<String[]> rawPoliticCards) {
		List<Card> cards = getCards();
		for(String[] rawPoliticCard : rawPoliticCards) {
			int sameColorPoliticNumber = Integer.parseInt(rawPoliticCard[0]);
			for(int i = 0; i < sameColorPoliticNumber; i++) {
				cards.add(new PoliticCard(GameColorFactory.makeColor(rawPoliticCard[2], rawPoliticCard[1])));
			}
		}
		return new PoliticDeck(cards);
	}
}
