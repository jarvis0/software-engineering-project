package it.polimi.ingsw.ps23.server.model.actions;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncilException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncillorException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;

public class ElectCouncillor implements Action {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8467179646741897111L;

	private static final int EARNED_COINS = 4;
	
	private String councillor;
	private Council council;
		
	public ElectCouncillor(String councillor, Council council) {
		this.councillor = councillor;
		this.council = council;
	}
	
	private void checkAction() throws InvalidCouncilException {
		if(council == null) {
			throw new InvalidCouncilException();
		}
	}

	@Override
	public void doAction(Game game, TurnHandler turnHandler) throws InvalidCouncillorException, InvalidCouncilException {
		checkAction();
		game.getFreeCouncillors().electCouncillor(councillor, council);
		game.getCurrentPlayer().updateCoins(EARNED_COINS);
		turnHandler.useMainAction();
	}
	
}
