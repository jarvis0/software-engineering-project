package it.polimi.ingsw.ps23.server.model.actions;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

@FunctionalInterface
public interface Action {
	
	public abstract void doAction(Game game, TurnHandler turnHandler);
	
}
