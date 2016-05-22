package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.Random;
	
public class Council{
	
	private final int numberOfCouncillors = 4;
	private final int lastPosition = 0;
	private ArrayList<Councillor> councilComposition;
	
	public Council(){
		councilComposition = new ArrayList<Councillor>(numberOfCouncillors);
	}
	
	@Override
	public String toString() {
		return this.getCouncil().toString();
	}
	
	public ArrayList<Councillor> getCouncil() {
		return councilComposition;
	}
	
	public void createCouncil(FreeCouncillors freeCouncillors){
		Random random = new Random();
		for(int i = 0; i < numberOfCouncillors; i++) {
				councilComposition.add(freeCouncillors.remove(random.nextInt(freeCouncillors.getSize())));
		}
	}
	
	public Councillor pushCouncillor(Councillor selectedCouncillor){
		Councillor removedCouncillor = councilComposition.remove(lastPosition);
		councilComposition.add(selectedCouncillor); 
		return removedCouncillor;
	}
	
	
	
}
