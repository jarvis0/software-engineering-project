package it.polimi.ingsw.ps23.server.model.map.regions;

import java.io.Serializable;
import java.util.Queue;
/**
 * Provide methods to manage the council.
 * @author mirma
 *
 */
public class Council implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4455873339388582926L;
	private Queue<Councillor> councilComposition;
	/**
	 * Constructs the council starting from a queue of free councillors
	 * @param councilComposition - queue of free councillors
	 */
	public Council(Queue<Councillor> councilComposition){
		this.councilComposition = councilComposition;
	}
	
	public Queue<Councillor> getCouncillors() {
		return councilComposition;
	}
	/**
	 * Remove the first element from the head queue and add the selected {@link Councillor} on the tail.
	 * @param selectedCouncillor - the added councillor
	 * @return the removed councillor
	 */
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
