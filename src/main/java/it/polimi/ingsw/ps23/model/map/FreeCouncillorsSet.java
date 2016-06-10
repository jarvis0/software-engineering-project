package it.polimi.ingsw.ps23.model.map;

import java.util.List;


public class FreeCouncillorsSet {
	
	private List<Councillor> freeCouncillors;
	
	public FreeCouncillorsSet(List<Councillor> freeCouncillors){
		this.freeCouncillors = freeCouncillors;
	}
	
	@Override
	public String toString() {
		return freeCouncillors.toString();
	}

	public List<Councillor> getFreeCouncillors() {
		return freeCouncillors;
	}
	 
	public Councillor remove(int i) {
		return freeCouncillors.remove(i);
	}
	
	public void electCouncillor(String councillorColor, Council nameCouncil) {
		for(Councillor freeCouncillor: freeCouncillors) {
			if(freeCouncillor.getColorName().equals(councillorColor)) {
				int i = freeCouncillors.indexOf(freeCouncillor);
				freeCouncillors.set(i, nameCouncil.pushCouncillor(freeCouncillors.get(i)));
				return;
			}
		}
	}	
	
}
