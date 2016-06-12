package it.polimi.ingsw.ps23.bonus;

import java.util.List;

import it.polimi.ingsw.ps23.model.player.Player;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;

public interface SuperBonus {
	
	public String checkBonus(Player currentPlayer);
	
	public void acquireSuperBonus(List<String> inputs, Game game, TurnHandler turnHandler);

}
