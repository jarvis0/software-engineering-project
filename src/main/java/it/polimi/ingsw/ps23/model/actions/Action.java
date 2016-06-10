package it.polimi.ingsw.ps23.model.actions;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;

@FunctionalInterface
public interface Action {
	
	public abstract void doAction(Game game, TurnHandler turnHandler);
	
}
