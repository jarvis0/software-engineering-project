package it.polimi.ingsw.ps23.model.map;

import java.util.LinkedList;
import java.util.Queue;

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
