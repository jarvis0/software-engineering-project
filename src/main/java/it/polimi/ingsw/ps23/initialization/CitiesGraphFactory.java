package it.polimi.ingsw.ps23.initialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import it.polimi.ingsw.ps23.model.map.CitiesGraph;
import it.polimi.ingsw.ps23.model.map.regions.City;

public class CitiesGraphFactory {	

	private static final int CITY_VERTEX_POSITION = 0;
	
	private Map<String, List<String>> citiesConnections;
	private CitiesGraph citiesGraph;
	
	public CitiesGraphFactory() {
		citiesConnections = new HashMap<>();
	}
	
	public void makeCitiesGraph(List<String[]> rawCitiesConnections, Map<String, City> cities) {
		DirectedGraph<City, DefaultEdge> rawCitiesGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
		for(String[] rawConnections : rawCitiesConnections) {
			rawCitiesGraph.addVertex(cities.get(rawConnections[CITY_VERTEX_POSITION]));
		}
		for(String[] rawConnections : rawCitiesConnections) {		
			List<String> currentConnections = new ArrayList<>();	
			for(int i = 1 ; i < rawConnections.length; i++) {
				rawCitiesGraph.addEdge(cities.get(rawConnections[CITY_VERTEX_POSITION]), cities.get(rawConnections[i]));
				currentConnections.add(rawConnections[i]);
			}
			citiesConnections.put(rawConnections[CITY_VERTEX_POSITION], currentConnections); 
		}
		citiesGraph = new CitiesGraph(rawCitiesGraph);
	}
	
	public CitiesGraph getCitiesGraph() {
		return citiesGraph;
	}
	
	public Map<String, List<String>> getCitiesConnections() {
		return citiesConnections;
	}
	
}
