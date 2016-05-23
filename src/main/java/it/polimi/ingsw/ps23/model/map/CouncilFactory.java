package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.Random;

public class CouncilFactory {
	
	private final int numberOfCouncillors = 4;
	private ArrayList<Councillor> councilComposition = new ArrayList<Councillor>(numberOfCouncillors);;
	
	public Council makeCouncil(FreeCouncillors freeCouncillors){
		Random random = new Random();
		for(int i = 0; i < numberOfCouncillors; i++) {
				councilComposition.add(freeCouncillors.remove(random.nextInt(freeCouncillors.getFreeCouncillors().size())));
		}
	return new Council(councilComposition);
	}
}
