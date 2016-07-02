package it.polimi.ingsw.ps23.server.model.actions;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncilException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncillorException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
/**
 * Provides methods to perform the specified game action if
 * the action is in a valid format.
 * @author Mirco Manzoni
 *
 */
@SuppressWarnings("serial")
public abstract class ElectCouncillorAction extends Action {

	private String councillor;
	private String council;
	private Map<String, Council> councilsMap;
	/**
	 * Constructs all specified action parameters.
	 * @param councillor - free councillor the player want to elect
	 * @param council - the council the player wants to elect a councillor in
	 */
	public ElectCouncillorAction(String councillor, String council) {
		this.councillor = councillor;
		this.council = council;
		councilsMap = new HashMap<>();
	}
	
	private void createCouncilMap(Game game) {
		councilsMap.put("king", game.getKing().getCouncil());
		for(Region region : game.getGameMap().getGroupRegionalCity()) {
			councilsMap.put(region.getName(), ((GroupRegionalCity) region).getCouncil());
		}
	}
	
	private void checkAction() throws InvalidCouncilException {
		if(councilsMap.get(council) == null) {
			throw new InvalidCouncilException();
		}
	}
	
	void elect(Game game) throws InvalidCouncilException, InvalidCouncillorException {
		createCouncilMap(game);
		checkAction();
		game.getFreeCouncillors().electCouncillor(councillor, councilsMap.get(council));
		setActionReport("Player " + game.getCurrentPlayer().getName() + "elect " + councillor + " councillor in " + council + " council");
	}
	
}
