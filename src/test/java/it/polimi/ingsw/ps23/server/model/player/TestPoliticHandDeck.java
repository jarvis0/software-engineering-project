package it.polimi.ingsw.ps23.server.model.player;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.GameColor;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;

public class TestPoliticHandDeck {

	@Test
	public void test() throws InvalidCardException {
		List<Card> cards = new ArrayList<>();
		GameColor gameColor = new GameColor("blue");
		Card card = new PoliticCard(gameColor);
		cards.add(card);
		Deck politicDeck = new Deck(cards);
		Player player = new Player("1", 2, 2, new PoliticHandDeck(politicDeck.pickCards(1)));
		player.getPoliticHandDeck().getCardInPosition(0).equals(card);
		assertTrue(player.getPoliticHandDeck().getCardInPosition(0).equals(card));
		player.getPoliticHandDeck().removeCard(card);
		assertTrue(player.getNumberOfPoliticCards() == 0);
		player.getPoliticHandDeck().addCard(card);
		assertTrue(player.getPoliticHandDeck().getAndRemove(0).equals(card) && player.getNumberOfPermitCards() == 0);
		assertTrue(player.getPoliticHandDeck().getCards().isEmpty());
		cards.add(card);
		List<Card> soldBuyCards = new ArrayList<>();
		soldBuyCards.addAll(cards);
		politicDeck = new Deck(cards);
		player.pickCard(politicDeck, 1);
		assertTrue(player.getPoliticHandDeck().getCardInPosition(0).equals(card) && player.getNumberOfPoliticCards() == 1);
		player.sellPoliticCards(soldBuyCards);
		assertTrue(player.getPoliticHandDeck().getHandSize() == 0);
		gameColor = new GameColor("multi");
		card = new PoliticCard(gameColor);
		List<Card> multiCards = new ArrayList<>();
		multiCards.add(card);
		soldBuyCards.add(card);
		player.buyPoliticCards(soldBuyCards);
		assertTrue(((PoliticHandDeck)(player.getPoliticHandDeck())).getJokerCardsNumber() == 1);
		List<String> multiCardsString = new ArrayList<>();
		multiCardsString.add("multi");
		assertTrue(multiCards.equals(((PoliticHandDeck)(player.getPoliticHandDeck())).getCardsByName(multiCardsString)));
		assertTrue(player.getPoliticHandDeck().getHandSize() == 2);
		List<String> removedCards = new ArrayList<>();
		removedCards.add("multi");
		removedCards.add("blue");
		try {
			assertTrue(((PoliticHandDeck)player.getPoliticHandDeck()).checkCost(removedCards) == -8);
		} catch (InvalidCardException e) { }
		gameColor = new GameColor("orange");
		card = new PoliticCard(gameColor);
		player.getPoliticHandDeck().addCard(card);
		player.getPoliticHandDeck().addCard(card);
		removedCards.add("orange");
		removedCards.add("orange");
		try {
			assertTrue(((PoliticHandDeck)player.getPoliticHandDeck()).checkCost(removedCards) == -1);
		} catch (InvalidCardException e) { }
		((PoliticHandDeck)player.getPoliticHandDeck()).removeCards(removedCards);
		assertTrue(player.getPoliticHandDeck().getHandSize() == 0);
	}

}
