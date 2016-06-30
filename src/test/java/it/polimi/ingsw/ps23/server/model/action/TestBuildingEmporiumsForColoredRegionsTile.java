package it.polimi.ingsw.ps23.server.model.action;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.actions.BuildEmporiumKing;
import it.polimi.ingsw.ps23.server.model.bonus.Bonus;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;

public class TestBuildingEmporiumsForColoredRegionsTile {

	@Test
	public void test() throws IOException {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		game.getCurrentPlayer().updateCoins(150);
		game.getCurrentPlayer().pickCard(game.getPoliticDeck(), 50);
		TurnHandler turnHandler = new TurnHandler();		
		List<Card> cards;
		List<String> cardsString;
		List<City> minCities = getMinCities(game);
		BuildEmporiumKing action;
		Bonus initialTile = game.getKingTilesSet().getCurrentTile();
		for(City city : minCities) {
			cards = getCards(game);
			cardsString = getStringFromCards(cards);
			action = new BuildEmporiumKing(cardsString, city.getName());
			action.doAction(game, turnHandler);
			turnHandler = new TurnHandler();
			assertTrue(game.getCurrentPlayer().getEmporiums().getBuiltEmporiumsSet().contains(city));
			assertTrue(game.getKing().getPosition().equals(city));
		}
		int initialPoints = game.getCurrentPlayer().getVictoryPoints();
		game.getCurrentPlayer().getAllTilesPoints(game, turnHandler);
		assertTrue(!game.getKingTilesSet().getCurrentTile().equals(initialTile));
		assertTrue(game.getCurrentPlayer().getVictoryPoints() > initialPoints);
	}
		
	private List<City> getMinCities(Game game) {
		List<City> minCities = new ArrayList<>();
		Set<Entry<String, City>> citiesEntry = game.getGameMap().getCities().entrySet();
		for (Entry<String, City> cityEntry : citiesEntry) {
			if (cityEntry.getValue().getColor().toString().equals(getColoredRegionColorLessCities(game))) {
				minCities.add(cityEntry.getValue());
			}
		}
		return minCities;
	}
	
	private String getColoredRegionColorLessCities(Game game) {
		Map<String, Integer> colorMap = new HashMap<>();
		Set<Entry<String, City>> citiesEntry = game.getGameMap().getCities().entrySet();
		for (Entry<String, City> cityEntry : citiesEntry) {
			if (!cityEntry.getValue().isCapital()) {
				int value = 0;
				if (colorMap.containsKey(cityEntry.getValue().getColor())) {
					value = colorMap.get(cityEntry.getValue().getColor()) + 1;
				}
				colorMap.put(cityEntry.getValue().getColor(), value);
			}
		}
		Set<Entry<String, Integer>> colorSet = colorMap.entrySet();
		Entry<String, Integer> minimum = null;
		int i = 0;
		for (Entry<String, Integer> color : colorSet) {
			if (i != 0) {
				if (color.getValue() < minimum.getValue()) {
					minimum = color;
				}
			} else {
				minimum = color;
			}
			i++;
		}
		return minimum.getKey();
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
