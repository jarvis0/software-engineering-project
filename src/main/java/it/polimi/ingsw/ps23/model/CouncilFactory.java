package it.polimi.ingsw.ps23.model;

import java.util.LinkedList;
import java.util.Queue;

import it.polimi.ingsw.ps23.model.map.Council;
import it.polimi.ingsw.ps23.model.map.Councillor;
import it.polimi.ingsw.ps23.model.map.FreeCouncillors;

public class CouncilFactory {
	
	private final int numberOfCouncillors = 4;
	private Queue<Councillor> councilComposition = new LinkedList<Councillor>();
	public Council makeCouncil(FreeCouncillors freeCouncillors){
		for(int i = 0; i < numberOfCouncillors; i++) {
				councilComposition.add(freeCouncillors.remove(freeCouncillors.getFreeCouncillors().size()-1));
		}
	return new Council(councilComposition);
	}
}
