package it.polimi.ingsw.ps23.server.model.actions;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncilException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncillorException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;

public class ElectCouncillor implements Action {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8467179646741897111L;

	private static final int EARNED_COINS = 4;
	
	private String councillor;
	private String council;
	private Map<String, Council> councilsMap;
		
	public ElectCouncillor(String councillor, String council) {
		this.councillor = councillor;
		this.council = council;
		councilsMap = new HashMap<>();
	}
	
	private void createCouncilMap(Game game) {
		for(Region region : game.getGameMap().getGroupRegionalCity()) {
			councilsMap.put(region.getName(), ((GroupRegionalCity) region).getCouncil());
		}
	}
	
	private void checkAction() throws InvalidCouncilException {
		if(councilsMap.get(council) == null) {
			throw new InvalidCouncilException();
		}
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) throws InvalidCouncillorException, InvalidCouncilException {
		createCouncilMap(game);
		checkAction();
		game.getFreeCouncillors().electCouncillor(councillor, councilsMap.get(council));
		game.getCurrentPlayer().updateCoins(EARNED_COINS);
		turnHandler.useMainAction();
	}
	
}
