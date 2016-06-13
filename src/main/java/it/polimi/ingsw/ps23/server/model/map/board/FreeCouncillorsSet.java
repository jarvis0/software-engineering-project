package it.polimi.ingsw.ps23.server.model.map.board;

import java.util.List;

import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;

public class FreeCouncillorsSet {
	
	private List<Councillor> freeCouncillors;
	
	public FreeCouncillorsSet(List<Councillor> freeCouncillors){
		this.freeCouncillors = freeCouncillors;
	}

	public List<Councillor> getFreeCouncillors() {
		return freeCouncillors;
	}
	 
	public Councillor remove(int i) {
		return freeCouncillors.remove(i);
	}
	
	public void electCouncillor(String councillorColor, Council nameCouncil) {
		for(Councillor freeCouncillor : freeCouncillors) {
			if(freeCouncillor.getColorName().equals(councillorColor)) {
				int i = freeCouncillors.indexOf(freeCouncillor);
				freeCouncillors.set(i, nameCouncil.pushCouncillor(freeCouncillors.get(i)));
				return;
			}
		}
	}	
	
	@Override
	public String toString() {
		return freeCouncillors.toString();
	}

}
