package it.polimi.ingsw.ps23.server.model.actions;

import java.io.Serializable;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

@FunctionalInterface
public interface Action extends Serializable {
	
	public abstract void doAction(Game game, TurnHandler turnHandler);
	
}
