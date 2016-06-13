package it.polimi.ingsw.ps23.server.model.initialization;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.initialization.GameColorFactory;
import it.polimi.ingsw.ps23.server.model.initialization.PoliticDeckFactory;
import it.polimi.ingsw.ps23.server.model.initialization.RawObject;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticDeck;

public class TestLoadPoliticDeck {
	
	private static final String TEST_CONFIGURATION_PATH = "src/test/java/it/polimi/ingsw/ps23/server/model/initialization/configuration/";
	private static final String POLITIC_DECK_CSV = "politicDeck.csv";

	@Test
	public void test() {
		List<String[]> rawPoliticCards = new RawObject(TEST_CONFIGURATION_PATH + POLITIC_DECK_CSV).getRawObject();
		PoliticDeck politicDeck = (PoliticDeck) new PoliticDeckFactory().makeDeck(rawPoliticCards);
		assertTrue(politicDeck.getDeck().size() == 20);
		politicDeck.pickCard();
		assertTrue(politicDeck.getDeck().size() == 19);
		politicDeck.pickCards(2);
		assertTrue(politicDeck.getDeck().size() == 17);
		boolean foundShuffled = false;
		boolean foundJolly = false;
		GameColor orange = GameColorFactory.makeColor("orange", "0xffa500");
		GameColor multi = GameColorFactory.makeColor("multi", "0xa1ff8f");
		for(int i = 0; i < (politicDeck.getDeck().size() - 1) / 2; i++) {
			if(politicDeck.getDeck().get(i) != politicDeck.getDeck().get(i+1)) {
				foundShuffled = true;
			}
			if(((PoliticCard)politicDeck.getDeck().get(i)).isJolly()) {
				foundJolly = true;
				assertTrue(multi.toString().equals(politicDeck.getDeck().get(i).toString()));
			}
		}
		assertTrue(foundShuffled && foundJolly);
		Card card = (PoliticCard) politicDeck.getDeck().get(0);
		assertTrue(multi.equals(((PoliticCard)card).getColor()) || orange.equals(((PoliticCard)card).getColor()));
		assertTrue(multi.isSameColor(((PoliticCard)card).getColor().getName()) || orange.isSameColor(((PoliticCard)card).getColor().getName()));
	}
}
