package it.polimi.ingsw.ps23.server.model.map.board;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncillorException;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;

public class FreeCouncillorsSet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3830904009427644527L;
	private List<Councillor> freeCouncillors;
	
	public FreeCouncillorsSet(List<Councillor> freeCouncillors){
		this.freeCouncillors = freeCouncillors;
	}

	public List<Councillor> getFreeCouncillorsList() {
		return freeCouncillors;
	}
	 
	public Councillor remove(int i) {
		return freeCouncillors.remove(i);
	}
	
	public void electCouncillor(String councillorColor, Council nameCouncil) throws InvalidCouncillorException {
		for(Councillor freeCouncillor : freeCouncillors) {
			if(freeCouncillor.toString().equals(councillorColor)) {
				int i = freeCouncillors.indexOf(freeCouncillor);
				freeCouncillors.set(i, nameCouncil.pushCouncillor(freeCouncillors.get(i)));
				return;
			}
		}
		throw new InvalidCouncillorException();
	}	
	
	@Override
	public String toString() {
		return freeCouncillors.toString();
	}

}
