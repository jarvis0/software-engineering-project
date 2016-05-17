package it.polimi.ingsw.ps23.model.map;

import java.util.ArrayList;

public class FreeCouncillors {
	protected ArrayList<Councillor> freeCouncillors;
	private FreeCouncillors(){
		freeCouncillors = new ArrayList<Councillor>();
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
