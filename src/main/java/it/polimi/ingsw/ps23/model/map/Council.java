package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;
import java.util.Random;
	
public class Council{
	
	private final int lastPosition = 0;
	private ArrayList<Councillor> councilComposition;
	
	public Council(ArrayList<Councillor> councilComposition){
		this.councilComposition = councilComposition;
	}
	
	@Override
	public String toString() {
		return this.getCouncil().toString();
	}
	
	public ArrayList<Councillor> getCouncil() {
		return councilComposition;
	}
	
	public Councillor pushCouncillor(Councillor selectedCouncillor){
		Councillor removedCouncillor = councilComposition.remove(lastPosition);
		councilComposition.add(selectedCouncillor); 
		return removedCouncillor;
	}
	
	
	
}
