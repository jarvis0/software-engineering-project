package it.polimi.ingsw.ps23.server.model.actions;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.model.map.regions.City;
import it.polimi.ingsw.ps23.model.player.Player;
import it.polimi.ingsw.ps23.model.player.PoliticHandDeck;


public class BuildEmporiumKing implements Action {

	private static final double ROAD_COST = -2;
	private City arriveCity;
	private List<String> removedCards;
	private City kingPosition;
	
	private Logger logger;
	
	public BuildEmporiumKing(List<String> removedCards, City arriveCity, City kingPosition) {
		this.removedCards = removedCards;
		this.arriveCity = arriveCity;
		this.kingPosition = kingPosition;
		logger = Logger.getLogger(this.getClass().getName());
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
			logger.log(Level.SEVERE, "Cannot build here.", e);
		} catch (InsufficientResourcesException e) {
			logger.log(Level.SEVERE, "Insufficient current player resources.", e);
		}
		player.checkEmporiumsGroups(game);
		turnHandler.useMainAction();
	}

}
