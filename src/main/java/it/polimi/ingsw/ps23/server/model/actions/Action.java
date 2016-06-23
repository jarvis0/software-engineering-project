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

@FunctionalInterface
public interface Action extends Serializable {
	
	public void doAction(Game game, TurnHandler turnHandler) throws InvalidCardException, InsufficientResourcesException, AlreadyBuiltHereException, InvalidCouncillorException, InvalidCouncilException, InvalidRegionException, InvalidCityException;
	
}
