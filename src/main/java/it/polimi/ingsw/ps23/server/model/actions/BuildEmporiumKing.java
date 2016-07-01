package it.polimi.ingsw.ps23.server.model.actions;

import java.util.List;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

import it.polimi.ingsw.ps23.server.commons.exceptions.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;

/**
 * Provides methods to perform the specified game action if
 * the action is in a valid format.
 * @author Alessandro Erba, Mirco Manzoni
 *
 */
public class BuildEmporiumKing implements Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3325202641887325997L;
	private static final double ROAD_COST = -2;
	private String arriveCity;
	private List<String> removedCards;
	/**
	 * Constructs all specified action parameters.
	 * @param removedCards - list of Politic card chosen
	 * @param arriveCity - arrival city of the king's walk
	 */
	public BuildEmporiumKing(List<String> removedCards, String arriveCity) {
		this.removedCards = removedCards;
		this.arriveCity = arriveCity;
	}
	
	private void checkAction(Game game) throws InvalidCityException {
		if(game.getGameMap().getCities().get(arriveCity) == null) {
			throw new InvalidCityException();
		}
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) throws InvalidCardException, InsufficientResourcesException, AlreadyBuiltHereException, InvalidCityException {
		checkAction(game);
		City finalCity = game.getGameMap().getCities().get(arriveCity);
		Player player = game.getCurrentPlayer();
		int cost = ((PoliticHandDeck) game.getCurrentPlayer().getPoliticHandDeck()).checkCost(removedCards);
		DijkstraShortestPath<City, DefaultEdge> dijkstraShortestPath = new DijkstraShortestPath<>(game.getGameMap().getCitiesGraph().getGraph(), game.getKing().getPosition() , finalCity);
		cost = cost + (int) (ROAD_COST * dijkstraShortestPath.getPathLength());
		if(Math.abs(cost) > player.getCoins()) {
			throw new InsufficientResourcesException();
		}
		int assistantsCost = finalCity.buildEmporium(player);
		((PoliticHandDeck) game.getCurrentPlayer().getPoliticHandDeck()).removeCards(removedCards);
		player.updateCoins(cost);
		player.updateAssistants(assistantsCost);
		game.getKing().setNewPosition(finalCity);
		player.updateEmporiumSet(game, turnHandler, finalCity);
		player.checkEmporiumsGroups(game);
		turnHandler.useMainAction();
	}

}
