package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;
import it.polimi.ingsw.ps23.model.map.CapitalCity;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.NormalCity;

public class CitiesGraph {
	
	private DirectedGraph<City, DefaultEdge> citiesGraph;
	
	public CitiesGraph(DirectedGraph<City, DefaultEdge> citiesGraph) {
		this.citiesGraph = citiesGraph;
	}
	
	public DirectedGraph<City, DefaultEdge> getGraph() {
		return citiesGraph;
	}
	@Override
	public String toString() {
		List<String> cities = new ArrayList<>();
		GraphIterator<City, DefaultEdge> iterator = new DepthFirstIterator<>(citiesGraph);
		while(iterator.hasNext()){
			 cities.add(iterator.next().toString());
		}
		return cities.toString(); 		//return "Cities: " + citiesGraph.toString().replace("[(", "\nConnections: [(");
	}
	

	public void getBonuses(Player player, City arriveCity) {
	List<City> citiesContainingPlayer = new ArrayList<>();
	citiesContainingPlayer.add(arriveCity);
	List<City> playerCityList = new ArrayList<>();
	playerCityList.addAll(player.getEmporiums().getBuiltEmporiumSet());
	searchCities(citiesContainingPlayer, playerCityList, player);
	}

	private void searchCities(List<City> citiesContainingPlayer, List<City> playerCityList, Player player) {
		for(int i=0; i < citiesContainingPlayer.size(); i++) {
			City cityAnalyzed = citiesContainingPlayer.get(i);
			List<City> successors = Graphs.successorListOf(citiesGraph, cityAnalyzed);
			successors.remove(cityAnalyzed);
			citiesContainingPlayer.remove(cityAnalyzed);
			for (City city1 : playerCityList) {
				if(successors.contains(city1)) {
					citiesContainingPlayer.add(city1);
				}
			}
			if(!(cityAnalyzed instanceof CapitalCity) ){
				((NormalCity)cityAnalyzed).useRewardToken(player);
			}
				playerCityList.remove(cityAnalyzed);
		}
		if(!citiesContainingPlayer.isEmpty()){
			searchCities(citiesContainingPlayer, playerCityList, player);
		}
	}
		
}


