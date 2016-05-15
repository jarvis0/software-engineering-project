package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.Random;

public class Council{
	private ArrayList<Councillor> councilComposition;
	private Council(){
		councilComposition = new ArrayList<Councillor>(4);
	}
	
	public ArrayList<Councillor> createCouncil(ArrayList<Councillor> freeCouncillors){
		Random random = new Random();
		for(int i = 0; i < councilComposition.size(); i++)
			{
				councilComposition.add(freeCouncillors.remove(random.nextInt()));
			}
		return councilComposition;
	}
	
	public Councillor pushCouncillor(Councillor selectedCouncillor){
		Councillor removedCouncillor = councilComposition.remove(0);
		councilComposition.add(selectedCouncillor);
		return removedCouncillor;
	}
	
	
	
}
