package it.polimi.ingsw.ps23.model.bonus;

import java.util.List;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.player.Player;

public interface SuperBonus {
	
	public String checkBonus(Player currentPlayer);
	
	public void acquireSuperBonus(List<String> inputs, Game game, TurnHandler turnHandler);

}
