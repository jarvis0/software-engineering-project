package it.polimi.ingsw.ps23.server.model.initialization;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.initialization.GameColorsBuilder;
import it.polimi.ingsw.ps23.server.model.initialization.PoliticCardsBuilder;
import it.polimi.ingsw.ps23.server.model.initialization.RawObject;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;

public class TestLoadPoliticDeck {
	
	private static final String TEST_CONFIGURATION_PATH = "src/test/java/it/polimi/ingsw/ps23/server/model/initialization/configuration/";
	private static final String POLITIC_DECK_CSV = "politicDeck.csv";

	@Test
	public void test() {
		List<String[]> rawPoliticCards = new RawObject(TEST_CONFIGURATION_PATH + POLITIC_DECK_CSV).getRawObject();
		Deck politicDeck = new PoliticCardsBuilder().makeDeck(rawPoliticCards);
		assertTrue(politicDeck.getCards().size() == 20);
		politicDeck.pickCard();
		assertTrue(politicDeck.getCards().size() == 19);
		politicDeck.pickCards(2);
		assertTrue(politicDeck.getCards().size() == 17);
		boolean foundShuffled = false;
		boolean foundJolly = false;
		GameColor orange = GameColorsBuilder.makeColor("orange");
		GameColor multi = GameColorsBuilder.makeColor("multi");
		for(int i = 0; i < (politicDeck.getCards().size() - 1) / 2; i++) {
			if(politicDeck.getCards().get(i) != politicDeck.getCards().get(i+1)) {
				foundShuffled = true;
			}
			if(((PoliticCard)politicDeck.getCards().get(i)).isJoker()) {
				foundJolly = true;
				assertTrue(multi.toString().equals(politicDeck.getCards().get(i).toString()));
			}
		}
		assertTrue(foundShuffled && foundJolly);
		Card card = (PoliticCard) politicDeck.getCards().get(0);
		assertTrue(multi.equals(((PoliticCard)card).getColor()) || orange.equals(((PoliticCard)card).getColor()));
		assertTrue(multi.isSameColor(((PoliticCard)card).getColor().toString()) || orange.isSameColor(((PoliticCard)card).getColor().toString()));
	}
}
