package it.polimi.ingsw.ps23.model.map;

import java.util.List;


public class FreeCouncillors {
	
	private List<Councillor> freeCouncillors;
	
	public FreeCouncillors(List<Councillor> freeCouncillors){
		this.freeCouncillors = freeCouncillors;
	}
	
	@Override
	public String toString() {
		return this.getFreeCouncillors().toString();
	}

	public List<Councillor> getFreeCouncillors() {
		return freeCouncillors;
	}
	 
	public Councillor remove(int i) {
		return freeCouncillors.remove(i);
	}
	
	public void selectCouncillor (int i, Council nameCouncil){
		freeCouncillors.set(i, nameCouncil.pushCouncillor(freeCouncillors.get(i)));
	}
	
	
}
