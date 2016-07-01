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
import it.polimi.ingsw.ps23.server.model.map.regions.BusinessPermitTile;

public class TestPermissionHandDeck {

	@Test
	public void test() throws InvalidCardException {
		List<Card> cards = new ArrayList<>();
		GameColor gameColor = new GameColor("blue");
		Card card = new PoliticCard(gameColor);
		cards.add(card);
		Deck politicDeck = new Deck(cards);
		Player player = new Player("1", 2, 2, new PoliticHandDeck(politicDeck.pickCards(1)));
		BusinessPermitTile permissionCard = new BusinessPermitTile();
		HandDeck handDeck = new PermitHandDeck();
		List<Card> permissionHandCards = new ArrayList<>();
		permissionHandCards.add(permissionCard);
		((PermitHandDeck)handDeck).addCards(permissionHandCards);
		player.buyPermitCards(permissionHandCards);
		assertTrue(permissionCard.equals(player.getPermitHandDeck().getCardInPosition(0)));
		player.sellPermitCards(permissionHandCards);
		assertTrue(player.getPermitHandDeck().getHandSize() == 0);
	}

}
