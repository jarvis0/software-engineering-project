package it.polimi.ingsw.ps23.server.model.bonus;

import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCityException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.TurnHandler;
import it.polimi.ingsw.ps23.server.model.player.Player;
/**
 * Provides methods to take a specific superbonus of nobility track
 * @author Alessandro Erba
 *
 */
public interface SuperBonus {
	
	public String checkBonus(Player currentPlayer);
	
	public void acquireSuperBonus(List<String> inputs, Game game, TurnHandler turnHandler) throws InvalidCardException, InvalidCityException;

}
