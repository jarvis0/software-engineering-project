package it.polimi.ingsw.ps23.model;

import java.util.List;

import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.PoliticCard;
import it.polimi.ingsw.ps23.model.map.PoliticDeck;

public class PoliticDeckFactory extends DeckFactory {
	
	private static final int SAME_COLOR_CARDS_POSITION = 0;
	private static final int CARD_COLOR_HEX_POSITION = 1;
	private static final int CARD_COLOR_NAME_POSITION = 2;
	
	public PoliticDeckFactory() {
		super();
	}
	
	public Deck makeDeck(List<String[]> rawPoliticCards) {
		List<Card> cards = getCards();
		for(String[] rawPoliticCard : rawPoliticCards) {
			int sameColorCardsNumber = Integer.parseInt(rawPoliticCard[SAME_COLOR_CARDS_POSITION]);
			for(int i = 0; i < sameColorCardsNumber; i++) {
				cards.add(new PoliticCard(GameColorFactory.makeColor(rawPoliticCard[CARD_COLOR_NAME_POSITION], rawPoliticCard[CARD_COLOR_HEX_POSITION])));
			}
		}
		return new PoliticDeck(cards);
	}
}
