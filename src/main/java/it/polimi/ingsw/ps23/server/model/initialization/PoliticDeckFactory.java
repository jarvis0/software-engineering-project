package it.polimi.ingsw.ps23.server.model.initialization;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;

class PoliticDeckFactory {
	
	private static final int SAME_COLOR_CARDS_POSITION = 0;
	private static final int CARD_COLOR_NAME_POSITION = 1;
	
	PoliticDeckFactory() {
		super();
	}
	
	Deck makeDeck(List<String[]> rawPoliticCards) {
		List<Card> cards = new ArrayList<>();
		for(String[] rawPoliticCard : rawPoliticCards) {
			int sameColorCardsNumber = Integer.parseInt(rawPoliticCard[SAME_COLOR_CARDS_POSITION]);
			for(int i = 0; i < sameColorCardsNumber; i++) {
				cards.add(new PoliticCard(GameColorFactory.makeColor(rawPoliticCard[CARD_COLOR_NAME_POSITION])));
			}
		}
		return new Deck(cards);
	}
}
