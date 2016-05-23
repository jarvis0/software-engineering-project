package it.polimi.ingsw.ps23.model.map;

import java.util.Queue;

public class Council{
	
	private Queue<Councillor> councilComposition;
	
	public Council(Queue<Councillor> councilComposition){
		this.councilComposition = councilComposition;
	}
	
	@Override
	public String toString() {
		return this.getCouncil().toString();
	}
	
	public Queue<Councillor> getCouncil() {
		return councilComposition;
	}
	
	public Councillor pushCouncillor(Councillor selectedCouncillor){
		Councillor removedCouncillor = councilComposition.remove();
		councilComposition.add(selectedCouncillor); 
		return removedCouncillor;
	}
	
	
	
}
