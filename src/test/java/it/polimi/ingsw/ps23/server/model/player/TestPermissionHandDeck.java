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
import it.polimi.ingsw.ps23.server.model.map.board.PoliticDeck;
import it.polimi.ingsw.ps23.server.model.map.regions.PermissionCard;

public class TestPermissionHandDeck {

	@Test
	public void test() throws InvalidCardException {
		List<Card> cards = new ArrayList<>();
		GameColor gameColor = new GameColor("blue", "0x0000ff");
		Card card = new PoliticCard(gameColor);
		cards.add(card);
		Deck politicDeck = new PoliticDeck(cards);
		Player player = new Player("1", 2, 2, new PoliticHandDeck(politicDeck.pickCards(1)));
		PermissionCard permissionCard = new PermissionCard();
		HandDeck handDeck = new PermissionHandDeck();
		List<Card> permissionHandCards = new ArrayList<>();
		permissionHandCards.add(permissionCard);
		((PermissionHandDeck)handDeck).addCards(permissionHandCards);
		assertTrue(((PermissionHandDeck)handDeck).getAvaiblePermissionCards().getCardInPosition(0).equals(permissionCard));
		//player.
	}

}
