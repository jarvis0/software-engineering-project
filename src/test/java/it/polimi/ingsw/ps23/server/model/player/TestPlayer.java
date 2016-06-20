package it.polimi.ingsw.ps23.server.model.player;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InsufficientResourcesException;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticDeck;

public class TestPlayer {

	@Test
	public void test() {
		List<Card> cards = new ArrayList<>();
		GameColor gameColor = new GameColor("blue", "0x0000ff");
		Card card = new PoliticCard(gameColor);
		cards.add(card);
		Deck politicDeck = new PoliticDeck(cards);
		Player player = new Player("1", 2, 2, new PoliticHandDeck(politicDeck.pickCards(1)));
		assertTrue(player.getName().equals("1"));
		assertTrue(player.getAssistants() == 2 && player.getCoins() == 2 && player.getVictoryPoints() == 0 && player.getNobilityTrackPoints() == 0 && player.getNumberOfPoliticCard() == 1);
		try {
			player.updateAssistants(1);
		} catch (InsufficientResourcesException e) {
			e.printStackTrace();
		}
		assertTrue(player.getAssistants() == 3);
		try {
			player.updateCoins(1);
		} catch (InsufficientResourcesException e) {
			e.printStackTrace();
		}
		assertTrue(player.getCoins() == 3);
		player.updateVictoryPoints(1);
		assertTrue(player.getVictoryPoints() == 1);
		player.updateNobilityPoints(1);
		assertTrue(player.getNobilityTrackPoints() == 1);
		player.getPoliticHandDeck().getCardInPosition(0).equals(card);
		assertTrue(player.getPoliticHandDeck().getCardInPosition(0).equals(card));
		player.getPoliticHandDeck().removeCard(card);
		assertTrue(player.getNumberOfPoliticCard() == 0);
		player.getPoliticHandDeck().addCard(card);
		assertTrue(player.getPoliticHandDeck().getAndRemove(0).equals(card) && player.getNumberOfPermissionCard() == 0);
		assertTrue(player.getPoliticHandDeck().getCards().isEmpty());
	}

}
