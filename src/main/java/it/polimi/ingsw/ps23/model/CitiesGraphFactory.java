package it.polimi.ingsw.ps23.model;

import java.util.List;
import java.util.Map;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import it.polimi.ingsw.ps23.model.map.City;

public class CitiesGraphFactory {	

	private static final int CITY_VERTEX_POSITION = 0;
	
	public CitiesGraph makeCitiesGraph(List<String[]> rawCitiesConnections, Map<String, City> cities) {
		DirectedGraph<City, DefaultEdge> citiesGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
		for(String[] rawConnections : rawCitiesConnections) {
			citiesGraph.addVertex(cities.get(rawConnections[CITY_VERTEX_POSITION]));
		}
		for(String[] rawConnections : rawCitiesConnections) {			
			for(int i = 1 ; i < rawConnections.length; i++){
				citiesGraph.addEdge(cities.get(rawConnections[CITY_VERTEX_POSITION]), cities.get(rawConnections[i]));
			}
		}
		return new CitiesGraph(citiesGraph);
	}
	
}
