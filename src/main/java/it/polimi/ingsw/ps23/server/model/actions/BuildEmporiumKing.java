package it.polimi.ingsw.ps23.server.model.actions;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

import it.polimi.ingsw.ps23.server.commons.exceptions.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;


public class BuildEmporiumKing implements Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3325202641887325997L;
	private static final double ROAD_COST = -2;
	private City arriveCity;
	private List<String> removedCards;
	private City kingPosition;
	
	public BuildEmporiumKing(List<String> removedCards, City arriveCity, City kingPosition) {
		this.removedCards = removedCards;
		this.arriveCity = arriveCity;
		this.kingPosition = kingPosition;
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		Player player = game.getCurrentPlayer();
		int assistantsCost = 0;
		int cost = ((PoliticHandDeck) player.getPoliticHandDeck()).removeCards(removedCards);
		DijkstraShortestPath<City, DefaultEdge> dijkstraShortestPath = new DijkstraShortestPath<>(game.getGameMap().getCitiesGraph().getGraph(), kingPosition , arriveCity);
		cost = cost + (int) (ROAD_COST * dijkstraShortestPath.getPathLength());
		try {
			assistantsCost = arriveCity.buildEmporium(player);
			player.updateCoins(cost);
			player.updateAssistants(assistantsCost);
			game.getKing().setNewPosition(arriveCity);
			player.updateEmporiumSet(game, turnHandler, arriveCity);
		} catch (AlreadyBuiltHereException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot build here.", e);
		} catch (InsufficientResourcesException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Insufficient current player resources.", e);
		}
		player.checkEmporiumsGroups(game);
		turnHandler.useMainAction();
	}

}
