package it.polimi.ingsw.ps23.model;

import java.util.HashMap;
import java.util.List;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polimi.ingsw.ps23.model.map.City;

public class CitiesGraph {	
	
	UndirectedGraph<City, DefaultEdge> citiesGraph;
	
	public CitiesGraph(List<String[]> citiesConnections, HashMap<String, City> cities) {
		citiesGraph = new SimpleGraph<>(DefaultEdge.class);
		for(String[] connections : citiesConnections) {
			citiesGraph.addVertex(cities.get(connections[0]));
		}
		for(String[] connections : citiesConnections) {			
			for(int i = 1 ; i < connections.length; i++){
				citiesGraph.addEdge(cities.get(connections[0]), cities.get(connections[i]));
			}
		}
	}
	
	@Override
	public String toString() {
		return "null";
	}
	

}
