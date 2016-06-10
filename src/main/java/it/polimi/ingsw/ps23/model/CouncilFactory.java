package it.polimi.ingsw.ps23.model;

import java.util.LinkedList;
import java.util.Queue;

import it.polimi.ingsw.ps23.model.map.Council;
import it.polimi.ingsw.ps23.model.map.Councillor;
import it.polimi.ingsw.ps23.model.map.FreeCouncillorSet;

public class CouncilFactory {
	
	private static final int COUNCILLORS_NUMBER = 4;
	
	public Council makeCouncil(FreeCouncillorSet freeCouncillors) {
		Queue<Councillor> councilComposition = new LinkedList<>();
		for(int i = 0; i < COUNCILLORS_NUMBER; i++) {
				councilComposition.add(freeCouncillors.remove(freeCouncillors.getFreeCouncillors().size() - 1));
		}
		return new Council(councilComposition);
	}
	
}
