package it.polimi.ingsw.ps23.server.model.actions;

import java.io.Serializable;

import it.polimi.ingsw.ps23.server.commons.exceptions.AlreadyBuiltHereException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InsufficientResourcesException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncilException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncillorException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidRegionException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

/**
 * This class provides do action method in order to perform
 * the object related action.
 * @author Alessandro Erba & Giuseppe Mascellaro & Mirco Manzoni
 *
 */
public abstract class Action implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7864642674984087880L;
	private String actionReport;
	/**
	 * Performs the specified game action.
	 * @param game
	 * @param turnHandler
	 * @throws InvalidCardException if the player has inserted a wrong both politic or permission card
	 * @throws InsufficientResourcesException if the player does not have game resources to perform the
	 * specified action
	 * @throws AlreadyBuiltHereException if the player attempts to construct an emporium on
	 * a city which he has already constructed
	 * @throws InvalidCouncillorException if the player chooses a councillor which does not exist in the
	 * selected council
	 * @throws InvalidCouncilException  if the player chooses a councillor which does not exist
	 * @throws InvalidRegionException if the player chooses an invalid region
	 * @throws InvalidCityException if the player refers to an invalid city
	 */
	public abstract void doAction(Game game, TurnHandler turnHandler) throws InvalidCardException, InsufficientResourcesException, AlreadyBuiltHereException, InvalidCouncillorException, InvalidCouncilException, InvalidRegionException, InvalidCityException;
	
	void setActionReport(String report) {
		actionReport = report;
	}
	
	@Override
	public String toString() {
		return actionReport;
	}
}
