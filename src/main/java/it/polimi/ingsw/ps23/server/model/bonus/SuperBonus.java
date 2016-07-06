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
	/**
	 * Checks if the current {@link Player} can take the specific bonus.
	 * @param currentPlayer - the current player 
	 * @return the output to show to the current player
	 */
	public String checkBonus(Player currentPlayer);
	/**
	 * Gives the current {@link SuperBonus} to the current {@link Player}
	 * @param inputs - input form the current player
	 * @param game - current game to take bonuses
	 * @param turnHandler - current turn handler to take bonuses
	 * @throws InvalidCardException if the player choose an invalid card
	 * @throws InvalidCityException if the palyer choose an invalid city
	 */
	public void acquireSuperBonus(List<String> inputs, Game game, TurnHandler turnHandler) throws InvalidCardException, InvalidCityException;

}
