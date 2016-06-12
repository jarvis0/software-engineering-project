package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.map.regions.CapitalCity;
import it.polimi.ingsw.ps23.model.map.regions.City;
import it.polimi.ingsw.ps23.model.map.regions.NormalCity;

public class CitiesGraph {
	
	private DirectedGraph<City, DefaultEdge> graph;
	
	public CitiesGraph(DirectedGraph<City, DefaultEdge> graph) {
		this.graph = graph;
	}
	
	public DirectedGraph<City, DefaultEdge> getGraph() {
		return graph;
	}
	
	@Override
	public String toString() {
		List<String> cities = new ArrayList<>();
		GraphIterator<City, DefaultEdge> iterator = new DepthFirstIterator<>(graph);
		while(iterator.hasNext()){
			 cities.add(iterator.next().toString());
		}
		return cities.toString();
		//TODO forse mai usata
	}	

	public void getBonuses(Game game, TurnHandler turnHandler, City arriveCity) {
		List<City> citiesContainingPlayer = new ArrayList<>();
		citiesContainingPlayer.add(arriveCity);
		List<City> playerCityList = new ArrayList<>();
		playerCityList.addAll(game.getCurrentPlayer().getEmporiums().getBuiltEmporiumSet());
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
		
}


