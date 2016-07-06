package it.polimi.ingsw.ps23.server.model.map.board;

import java.io.Serializable;
import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncillorException;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.Councillor;
/**
 * Provides methods to manage all the councillors in game that aren't in a council
 * @author Alessandro Erba
 *
 */
public class FreeCouncillorsSet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3830904009427644527L;
	private List<Councillor> freeCouncillors;
	/**
	 * Constructs the free councillors set starting from a list of councillors
	 * @param freeCouncillors - the list of starting councillors
	 */
	public FreeCouncillorsSet(List<Councillor> freeCouncillors){
		this.freeCouncillors = freeCouncillors;
	}

	public List<Councillor> getFreeCouncillorsList() {
		return freeCouncillors;
	}
	/**
	 * Gets a councillor removed from the selected position.
	 * @param i - the position of the councillor
	 * @return the removed councillor.
	 */
	public Councillor remove(int i) {
		return freeCouncillors.remove(i);
	}
	/**
	 * Takes the selected councillors from the list and puts it in the selected council. The councillor removed
	 * from the selected council will be added in the free councillors set
	 * @param councillorColor - the color of the free councillor selected
	 * @param nameCouncil - the name of the council
	 * @throws InvalidCouncillorException if an invalid councillor has been selected.
	 */
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
