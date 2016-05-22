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
	
	public void insertNewFreeCouncillor(Councillor councillor){
		freeCouncillors.add(councillor);
	}

	public ArrayList<Councillor> getFreeCouncillors() {
		return freeCouncillors;
	}
	
	public void selectCouncillor (int i, Council nameCouncil){
		insertNewFreeCouncillor(nameCouncil.pushCouncillor(freeCouncillors.remove(i)));
	}
	
	
}
