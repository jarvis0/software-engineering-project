package it.polimi.ingsw.ps23.server.model.initialization;

import java.util.LinkedList;
import java.util.Queue;

import it.polimi.ingsw.ps23.server.model.map.board.FreeCouncillorsSet;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;

class CouncilBuilder {
	
	private static final int COUNCILLORS_NUMBER = 4;
	
	Council makeCouncil(FreeCouncillorsSet freeCouncillors) {
		Queue<Councillor> councilComposition = new LinkedList<>();
		for(int i = 0; i < COUNCILLORS_NUMBER; i++) {
			councilComposition.add(freeCouncillors.remove(freeCouncillors.getFreeCouncillorsList().size() - 1));
		}
		return new Council(councilComposition);
	}
	
}
