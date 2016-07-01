package it.polimi.ingsw.ps23.server.model.actions;

import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidRegionException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
/**
 * Provides methods to perform the specified game action if
 * the action is in a valid format.
 * @author Alessandro Erba, Mirco Manzoni
 *
 */
public class ChangePermitsTile implements Action {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4131871312323174768L;
	private static final int ASSISTANTS_COST = -1;
	private String regionName;
	/**
	 * Constructs all specified action parameters.
	 * @param regionName - the selected region where the current player wants to change the business permit tile
	 */
	public ChangePermitsTile(String regionName) {
		this.regionName = regionName;
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) throws InsufficientResourcesException, InvalidRegionException {
		if(Math.abs(ASSISTANTS_COST) > game.getCurrentPlayer().getAssistants()) {
			throw new InsufficientResourcesException();
		}
		if(((GroupRegionalCity) game.getGameMap().getRegion(regionName)) == null) {
			throw new InvalidRegionException();
		}
		game.getCurrentPlayer().updateAssistants(ASSISTANTS_COST);
		((GroupRegionalCity) game.getGameMap().getRegion(regionName)).changePermitTiles();
		turnHandler.useQuickAction();
	}

}
