package it.polimi.ingsw.ps23.model;

import java.util.List;

import it.polimi.ingsw.ps23.model.map.Deck;
import it.polimi.ingsw.ps23.model.map.PoliticCard;
import it.polimi.ingsw.ps23.model.map.PoliticDeck;

public class PoliticDeckFactory extends DeckFactory {
	
	public PoliticDeckFactory() {
		super();
	}
	
	@Override
	public Deck makeDeck(List<String[]> rawPoliticCards) {
		for(String[] rawPoliticCard : rawPoliticCards) {
			int sameColorPoliticNumber = Integer.parseInt(rawPoliticCard[0]);
			for(int i = 0; i < sameColorPoliticNumber; i++) {
				getCards().add(new PoliticCard(GameColorFactory.makeColor(rawPoliticCard[2], rawPoliticCard[1])));
			}
		}
		return new PoliticDeck(getCards());
	}
}
