package it.polimi.ingsw.ps23.model;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import it.polimi.ingsw.ps23.model.map.City;

public class CitiesGraph {
	
	private DirectedGraph<City, DefaultEdge> citiesGraph;
	
	public CitiesGraph(DirectedGraph<City, DefaultEdge> citiesGraph) {
		this.citiesGraph = citiesGraph;
	}
	
	@Override
	public String toString() {
 		return "Cities: " + citiesGraph.toString().replace("[(", "\nConnections: [(");
	}
	
}
