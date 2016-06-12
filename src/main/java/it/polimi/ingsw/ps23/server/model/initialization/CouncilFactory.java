package it.polimi.ingsw.ps23.server.model.initialization;

import java.util.LinkedList;
import java.util.Queue;

import it.polimi.ingsw.ps23.model.map.board.FreeCouncillorsSet;
import it.polimi.ingsw.ps23.model.map.regions.Council;
import it.polimi.ingsw.ps23.model.map.regions.Councillor;

public class CouncilFactory {
	
	private static final int COUNCILLORS_NUMBER = 4;
	
	public Council makeCouncil(FreeCouncillorsSet freeCouncillors) {
		Queue<Councillor> councilComposition = new LinkedList<>();
		for(int i = 0; i < COUNCILLORS_NUMBER; i++) {
				councilComposition.add(freeCouncillors.remove(freeCouncillors.getFreeCouncillors().size() - 1));
		}
		return new Council(councilComposition);
	}
	
}
