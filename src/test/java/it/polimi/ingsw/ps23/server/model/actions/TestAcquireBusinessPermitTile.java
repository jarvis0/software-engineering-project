package it.polimi.ingsw.ps23.server.model.actions;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidRegionException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.actions.AcquireBusinessPermitTile;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
/**
 * Tests the mechanics of the {@link AcquireBusinessPermitTile} action and all classes involved in.
 *
 */
public class TestAcquireBusinessPermitTile {

	@Test
	public void test() throws InvalidCardException, InsufficientResourcesException, InvalidRegionException {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		game.getCurrentPlayer().updateCoins(10);
		game.getCurrentPlayer().pickCard(game.getPoliticDeck(), 30);
		TurnHandler turnHandler = new TurnHandler();
		Council council;
		List<Card> cards = new ArrayList<>();
		List<String> cardsString = new ArrayList<>();
		int i;
		for(i = 0; i < game.getGameMap().getGroupRegionalCity().size() && cards.isEmpty(); i++) {
			if(!(((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(i))).getPermitTilesUp().toString().contains("politic") || ((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(i))).getPermitTilesUp().toString().contains("additional"))) {
				council = ((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(i))).getCouncil();
				for(Card card : game.getCurrentPlayer().getPoliticHandDeck().getCards()) {
					Iterator<Councillor> iterator = council.getCouncillors().iterator();
					while(iterator.hasNext()) {
						if(iterator.next().getColor().toString().equals(((PoliticCard)card).getColor().toString()) && cards.size() < 4 && !cardsString.contains(((PoliticCard)card).getColor().toString())) {
							cards.add(card);
							cardsString.add(((PoliticCard)card).getColor().toString());
						}
					}
				}
			}
			else {
				((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(i))).changePermitTiles();
				i--;
			}
		}
		i--;
		int initialCards = game.getCurrentPlayer().getNumberOfPoliticCards();
		List<Card> permissionCard = new ArrayList<>();
		permissionCard.add(((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(i))).getPermitTilesUp().getCards().get(0));
		System.out.println(cardsString + game.getGameMap().getGroupRegionalCity().get(i).getName() + permissionCard.get(0));
		AcquireBusinessPermitTile action = new AcquireBusinessPermitTile(cardsString, game.getGameMap().getGroupRegionalCity().get(i).getName(), 0);
		action.doAction(game, turnHandler);
		assertFalse(turnHandler.isAvailableMainAction());
		assertTrue(initialCards == game.getCurrentPlayer().getNumberOfPoliticCards() + cards.size());
		assertTrue(game.getCurrentPlayer().getAllPermitHandDeck().getHandSize() == 1);
		assertTrue(game.getCurrentPlayer().getNumberOfPermitCards() == 1);	
	}

}
