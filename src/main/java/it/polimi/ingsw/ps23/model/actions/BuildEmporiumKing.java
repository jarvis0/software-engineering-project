package it.polimi.ingsw.ps23.model.actions;

import java.util.List;

import javax.naming.InsufficientResourcesException;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.PoliticHandDeck;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.model.map.City;


public class BuildEmporiumKing extends MainAction {

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
		int cost = ((PoliticHandDeck) game.getCurrentPlayer().getPoliticHandDeck()).removeCards(removedCards);
		DijkstraShortestPath<City, DefaultEdge> dijkstraShortestPath = new DijkstraShortestPath<>(game.getGameMap().getCitiesGraph().getGraph(), kingPosition , arriveCity);
		cost = cost + (int) (ROAD_COST*dijkstraShortestPath.getPathLength());
		City city = game.getGameMap().getCitiesMap().get(arriveCity.getName());
		
			try {
				cost = cost + city.buildEmporium(game.getCurrentPlayer());
				
			} catch (AlreadyBuiltHereException e) {
				e.printStackTrace();
			}
			
			try {
				game.getKing().setNewPosition(arriveCity);
				game.getCurrentPlayer().updateEmporiumSet(arriveCity);
				game.getCurrentPlayer().updateCoins(cost);
			} catch (InsufficientResourcesException e) {
				e.printStackTrace();
			}
		
		
		//GraphIterator<City, DefaultEdge> iterator = new DepthFirstIterator<>(game.getGameMap().getCitiesGraph().getGraph(),arriveCity);
		//City visitedCity;
		//while(visitedCity.getEmporiums().contains(game.getCurrentPlayer()) == true) {	
			
		
		turnHandler.useMainAction();
	}

}
