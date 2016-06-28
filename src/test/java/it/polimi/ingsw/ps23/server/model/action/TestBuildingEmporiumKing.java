package it.polimi.ingsw.ps23.server.model.action;

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

public class TestBuildingEmporiumKing {

	@Test
	public void test() throws IOException {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		playersName.add("b");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		game.getCurrentPlayer().updateCoins(50);
		game.getCurrentPlayer().pickCard(game.getPoliticDeck(), 20);
		TurnHandler turnHandler = new TurnHandler();		
		List<Card> cards = getCards(game);
		List<String> cardsString = getStringFromCards(cards);
		City capital = getCapitalCity(game);
		BuildEmporiumKing action = new BuildEmporiumKing(cardsString, capital.getName());
		action.doAction(game, turnHandler);
		assertFalse(turnHandler.isAvailableMainAction());
		assertTrue(game.getCurrentPlayer().getEmporiums().getBuiltEmporiumsSet().contains(capital));
		turnHandler = new TurnHandler();
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(1));
		game.getCurrentPlayer().updateCoins(50);
		game.getCurrentPlayer().pickCard(game.getPoliticDeck(), 30);
		cards = getCards(game);
		cardsString = getStringFromCards(cards);
		action = new BuildEmporiumKing(cardsString, capital.getName());
		int initialAssistant = game.getCurrentPlayer().getAssistants();
		action.doAction(game, turnHandler);
		assertTrue(game.getCurrentPlayer().getAssistants() + 1 == initialAssistant);		
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

	private City getCapitalCity(Game game) {
		Set<Entry<String, City>> citiesEntry = game.getGameMap().getCities().entrySet();
		for (Entry<String, City> cityEntry : citiesEntry) {
			if (cityEntry.getValue().isCapital()) {
				return cityEntry.getValue();
			}
		}
		return null;
	}

}
