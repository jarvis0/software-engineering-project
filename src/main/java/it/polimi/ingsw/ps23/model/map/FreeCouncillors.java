package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;

import it.polimi.ingsw.ps23.model.GameColor;

public class FreeCouncillors {
	
	private ArrayList<Councillor> freeCouncillors;
	
	public FreeCouncillors(ArrayList<Councillor> freeCouncillors){
		this.freeCouncillors = freeCouncillors;
	}
	
	@Override
	public String toString() {
		return this.getFreeCouncillors().toString();
	}

	public ArrayList<Councillor> getFreeCouncillors() {
		return freeCouncillors;
	}
	 
	public Councillor remove(int i) {
		return freeCouncillors.remove(i);
	}
	
	public int getSize() {
		return freeCouncillors.size();
	}
	
	public void selectCouncillor (int i, Council nameCouncil){
		insertNewFreeCouncillor(nameCouncil.pushCouncillor(freeCouncillors.remove(i)));
	}
	
	public void insertNewFreeCouncillor(Councillor councillor){
		freeCouncillors.add(councillor);
	}
	
	
}
