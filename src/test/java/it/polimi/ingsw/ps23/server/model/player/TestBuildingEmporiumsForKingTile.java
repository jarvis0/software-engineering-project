package it.polimi.ingsw.ps23.server.model.player;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.actions.BuildEmporiumKing;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;
/**
 * Tests the mechanics of the {@link BuildingEmporiumsKing} action and all classes involved in to check when
 * the {@link KingTile} were picked.
 * @author Mirco Manzoni
 * 
 */
public class TestBuildingEmporiumsForKingTile {

	@Test
	public void test() throws IOException {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		game.getCurrentPlayer().updateCoins(700);
		game.getCurrentPlayer().pickCard(game.getPoliticDeck(), 60);
		TurnHandler turnHandler = new TurnHandler();		
		List<Card> cards;
		List<String> cardsString;
		BuildEmporiumKing action;
		Set<Entry<String, City>> citiesEntry = game.getGameMap().getCities().entrySet();
		for(Entry<String, City> cityEntry : citiesEntry) {
			cards = getCards(game);
			cardsString = getStringFromCards(cards);
			action = new BuildEmporiumKing(cardsString, cityEntry.getValue().getName());
			action.doAction(game, turnHandler);
			turnHandler = new TurnHandler();
			assertTrue(game.getCurrentPlayer().getEmporiums().getBuiltEmporiumsSet().contains(cityEntry.getValue()));
			assertTrue(game.getKing().getPosition().equals(cityEntry.getValue()));
		}
		assertTrue(game.getKingTilesSet().isEmpty());
	}
	
	private List<String> getStringFromCards(List<Card> cards) {
		List<String> cardsString = new ArrayList<>();
		for(Card card : cards) {
			cardsString.add(((PoliticCard) card).getColor().toString());
		}
		return cardsString;
	}
	
	private List<Card> getCards(Game game) {
		Council council = game.getKing().getCouncil();
		PoliticHandDeck deck = (PoliticHandDeck) ((PoliticHandDeck)(game.getCurrentPlayer().getPoliticHandDeck())).getAvailableCards(council);
		return deck.getCards();
	}

}
