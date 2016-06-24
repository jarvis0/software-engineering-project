package it.polimi.ingsw.ps23.server.model.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.regions.CapitalCity;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.NormalCity;

/**
 * Provides a graph representation to game cities and algorithms to explore
 * the graph with the minimum walk way.
 * @author Alessandro Erba & Mirco Manzoni
 *
 */
public class CitiesGraph implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 777126013147994317L;
	private transient DirectedGraph<City, DefaultEdge> graph;
	
	/**
	 * Saves the already created graph from citiesFactory in order to
	 * easy manipulate this object for further uses.
	 * @param graph
	 */
	public CitiesGraph(DirectedGraph<City, DefaultEdge> graph) {
		this.graph = graph;
	}
	
	public DirectedGraph<City, DefaultEdge> getGraph() {
		return graph;
	}
	
	/**
	 * Calculates the minimum walk into the game cities graph and gives
	 * the reached rewards token to the player.
	 * @param game
	 * @param turnHandler
	 * @param arriveCity
	 */
	public void rewardTokenGiver(Game game, TurnHandler turnHandler, City arrivalCity) {
		List<City> citiesContainingPlayer = new ArrayList<>();
		citiesContainingPlayer.add(arrivalCity);
		List<City> playerCityList = new ArrayList<>();
		playerCityList.addAll(game.getCurrentPlayer().getEmporiums().getBuiltEmporiumsSet());
		searchCities(citiesContainingPlayer, playerCityList, game, turnHandler);
	}

	private void searchCities(List<City> citiesContainingPlayer, List<City> playerCityList, Game game, TurnHandler turnHandler) {
		for(int i=0; i < citiesContainingPlayer.size(); i++) {
			City cityAnalyzed = citiesContainingPlayer.get(i);
			List<City> successors = Graphs.successorListOf(graph, cityAnalyzed);
			successors.remove(cityAnalyzed);
			citiesContainingPlayer.remove(cityAnalyzed);
			for(City city1 : playerCityList) {
				if(successors.contains(city1)) {
					citiesContainingPlayer.add(city1);
				}
			}
			if(!(cityAnalyzed instanceof CapitalCity)){
				((NormalCity)cityAnalyzed).useRewardToken(game, turnHandler);
			}
			playerCityList.remove(cityAnalyzed);
		}
		if(!citiesContainingPlayer.isEmpty()){
			searchCities(citiesContainingPlayer, playerCityList, game, turnHandler);
		}
	}
	
	/*@Override
	public String toString() {
		List<String> cities = new ArrayList<>();
		GraphIterator<City, DefaultEdge> iterator = new DepthFirstIterator<>(graph);
		while(iterator.hasNext()){
			 cities.add(iterator.next().toString());
		}
		return cities.toString();
		//TODO forse mai usata
	}*/
		
}


