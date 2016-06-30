package it.polimi.ingsw.ps23.server.model.map.regions;

import java.io.Serializable;
import java.util.Queue;

public class Council implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4455873339388582926L;
	private Queue<Councillor> councilComposition;
	
	public Council(Queue<Councillor> councilComposition){
		this.councilComposition = councilComposition;
	}
	
	public Queue<Councillor> getCouncillors() {
		return councilComposition;
	}
	
	public Councillor pushCouncillor(Councillor selectedCouncillor) {
		Councillor removedCouncillor = councilComposition.remove();
		councilComposition.add(selectedCouncillor); 
		return removedCouncillor;
	}
	
	@Override
	public String toString() {
		return councilComposition.toString();
	}
	
}
