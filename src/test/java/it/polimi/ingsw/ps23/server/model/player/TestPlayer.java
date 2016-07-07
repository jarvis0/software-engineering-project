package it.polimi.ingsw.ps23.server.model.player;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
/**
 * Tests all the methods of the {@link Player} that give him points or cards.
 * @author mirma
 *
 */
public class TestPlayer {

	@Test
	public void test() {
		List<Card> cards = new ArrayList<>();
		GameColor gameColor = new GameColor("blue");
		Card card = new PoliticCard(gameColor);
		cards.add(card);
		Deck politicDeck = new Deck(cards);
		Player player = new Player("1", 2, 2, new PoliticHandDeck(politicDeck.pickCards(1)));
		assertTrue(player.getName().equals("1"));
		assertTrue(player.getAssistants() == 2 && player.getCoins() == 2 && player.getVictoryPoints() == 0 && player.getNobilityTrackPoints() == 0 && player.getNumberOfPoliticCards() == 1);
		player.updateAssistants(1);
		assertTrue(player.getAssistants() == 3);
		player.updateCoins(1);
		assertTrue(player.getCoins() == 3);
		player.updateVictoryPoints(1);
		assertTrue(player.getVictoryPoints() == 1);
		player.updateNobilityPoints(1);
		assertTrue(player.getNobilityTrackPoints() == 1);
		player.setOnline(false);
		assertFalse(player.isOnline());
		assertTrue(!player.showSecretStatus().isEmpty());
		assertTrue(player.toString().contains("offline"));
	}

}
