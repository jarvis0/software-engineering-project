package it.polimi.ingsw.ps23.server.model.market;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.Deck;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.server.model.player.Player;
/**
 * Tests the core object of the market phase, the {@link MarketTransaction}. It tests all the player's
 * parameters before and after the transaction.
 * @author Mirco Manzoni
 *
 */
public class TestMarketTransation {

	@Test
	public void test() throws InvalidCardException {
		MarketTransaction marketTransaction = new MarketTransaction();
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		playersName.add("b");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayer(1));
		game.createNewMarket();
		assertTrue(game.getMarketPlayersNumber() == 2);
		Player player = game.getGamePlayersSet().getPlayer(0);
		game.getCurrentPlayer().getPoliticHandDeck().getCards().clear();
		List<Card> politicCards = new ArrayList<>();
		List<String> politicCardsString = new ArrayList<>();
		politicCards.add(player.getPoliticHandDeck().getCardInPosition(0));
		politicCards.add(player.getPoliticHandDeck().getCardInPosition(1));
		politicCardsString.add(((PoliticCard)(player.getPoliticHandDeck().getCardInPosition(0))).getColor().toString());
		politicCardsString.add(((PoliticCard)(player.getPoliticHandDeck().getCardInPosition(1))).getColor().toString());
		List<Integer> permissionCards = new ArrayList<>();
		Iterator<Deck> iterator = game.getGameMap().getPermissionCardsUp().values().iterator();
		Card card = iterator.next().getCards().get(0);
		List<Card> cards = new ArrayList<>();
		cards.add(card);
		permissionCards.add(0);
		player.buyPermitCards(cards);
		int assistants = 1;
		int cost = 10;
		MarketObject marketObject = new MarketObject(player.getName(), permissionCards, politicCardsString, assistants, cost);
		game.getMarket().addMarketObject(marketObject);
		marketTransaction.setRequestedObject(marketObject);
		int sellerPoliticHandSize = player.getNumberOfPoliticCards();
		int sellerCoins = player.getCoins();
		int sellerAssistants = player.getAssistants();
		int buyerCoins = game.getCurrentPlayer().getCoins();
		int buyerAssistants = game.getCurrentPlayer().getAssistants();
		marketTransaction.doTransation(game);
		assertTrue(player.getNumberOfPoliticCards() == sellerPoliticHandSize - politicCards.size());
		assertTrue(player.getNumberOfPermitCards() == 0);
		assertTrue(player.getCoins() == sellerCoins + cost);
		assertTrue(player.getAssistants() == sellerAssistants - assistants);
		assertTrue(game.getCurrentPlayer().getPoliticHandDeck().getCards().containsAll(politicCards));
		assertTrue(game.getCurrentPlayer().getPermitHandDeck().getCardInPosition(0).equals(card));
		assertTrue(game.getCurrentPlayer().getCoins() == buyerCoins - cost);
		assertTrue(game.getCurrentPlayer().getAssistants() == buyerAssistants + assistants);
		marketTransaction.notPurchased();
		sellerCoins = player.getCoins();
		buyerCoins = game.getCurrentPlayer().getCoins();
		assertTrue(player.getCoins() == sellerCoins);
		assertTrue(game.getCurrentPlayer().getCoins() == buyerCoins);
	}

}
