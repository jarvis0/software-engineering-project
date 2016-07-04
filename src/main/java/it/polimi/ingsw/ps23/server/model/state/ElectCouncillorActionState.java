package it.polimi.ingsw.ps23.server.model.state;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.board.FreeCouncillorsSet;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;

abstract class ElectCouncillorActionState extends ActionState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3353345238431265969L;
	private FreeCouncillorsSet freeCouncillors;
	private Map<String, Council> councilsMap;
	
	ElectCouncillorActionState(String name) {
		super(name);
		councilsMap = new HashMap<>();
	}
	
	public String getFreeCouncillors() {
		return freeCouncillors.toString();
	}
	
	public String getCouncilsMap() {
		return councilsMap.toString();
	}
	
	void setParameters(Game game) {
		freeCouncillors = game.getFreeCouncillors();
		councilsMap.put("king", game.getKing().getCouncil());
		for(Region region : game.getGameMap().getGroupRegionalCity()) {
			councilsMap.put(region.getName(), ((GroupRegionalCity) region).getCouncil());
		}
	}
	/**
	 * Create the object to perform the action that the user had selected.
	 * @param chosenCouncillor - the name of the color of the councillor selected
	 * @param chosenBalcony - the name of the selected council
	 * @return the action created
	 */
	public abstract Action createAction(String chosenCouncillor, String chosenBalcony);

}
