package it.polimi.ingsw.ps23.server.model.action;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import it.polimi.ingsw.ps23.server.commons.exceptions.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.actions.BuildEmporiumPermitTile;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.map.board.PoliticCard;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.BusinessPermitTile;
/**
 * Tests the mechanics of the {@link BuildingEmporiumPermitTile} action and all classes involved in.
 * @author Mirco Manzoni
 *
 */
public class TestBuildingEmporiumPermitTile {

	@Test
	public void test() throws InsufficientResourcesException, AlreadyBuiltHereException, InvalidCityException, InvalidCardException {
		List<String> playersName = new ArrayList<>();
		playersName.add("a");
		Game game = new Game(playersName);
		game.setCurrentPlayer(game.getGamePlayersSet().getPlayers().get(0));
		game.getCurrentPlayer().updateCoins(20);
		TurnHandler turnHandler = new TurnHandler();
		int i;
		Council council;
		List<Card> cards = new ArrayList<>();
		List<String> cardsString = new ArrayList<>();
		for (i = 0; i < game.getGameMap().getGroupRegionalCity().size() && cards.isEmpty(); i++) {
			council = ((GroupRegionalCity) (game.getGameMap().getGroupRegionalCity().get(i))).getCouncil();
			for (Card card : game.getCurrentPlayer().getPoliticHandDeck().getCards()) {
				Iterator<Councillor> iterator = council.getCouncillors().iterator();
				while (iterator.hasNext()) {
					if (iterator.next().getColor().toString().equals(((PoliticCard) card).getColor().toString()) && cards.size() < 4 && !cardsString.contains(((PoliticCard) card).getColor().toString())) {
						cards.add(card);
						cardsString.add(((PoliticCard) card).getColor().toString());
					}
				}
			}
		}
		i--;
		List<Card> permissionCards = new ArrayList<>();
		permissionCards.add(((GroupRegionalCity)(game.getGameMap().getGroupRegionalCity().get(i))).getPermitTilesUp().getCards().get(0));
		game.getCurrentPlayer().buyPermitCards(permissionCards);
		Set<Entry<String, City>> citiesEntry = game.getGameMap().getCities().entrySet();
		City city = null;
		String cityString = new String();
		for(Entry<String, City> cityEntry : citiesEntry) {
			if(((BusinessPermitTile)permissionCards.get(0)).containCity(cityEntry.getValue())) {
				city = cityEntry.getValue();
				cityString = cityEntry.getKey();
			}
		}
		BuildEmporiumPermitTile action = new BuildEmporiumPermitTile(cityString, 0);
		action.doAction(game, turnHandler);
		assertFalse(turnHandler.isAvailableMainAction());
		assertTrue(game.getCurrentPlayer().getEmporiums().getBuiltEmporiumsSet().contains(city));
		assertTrue(game.getCurrentPlayer().getNumberOfPermitCards() == 1);
		assertTrue(game.getCurrentPlayer().getAllPermitHandDeck().getCards().size() == 1);
		assertTrue(game.getCurrentPlayer().getPermitHandDeck().getCards().isEmpty());
		
	}

}