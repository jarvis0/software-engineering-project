package it.polimi.ingsw.ps23.server.model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Provides methods to manage the set of players that are playing the current game.
 * @author Giuseppe Mascellaro
 *
 */
public class PlayersSet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2043417363819650294L;
	private List<Player> players;
	/**
	 * Inizialise the set to the starting value.
	 */
	public PlayersSet() {
		players = new ArrayList<>();
	}
	/**
	 * Add a {@link Player} to the set.
	 * @param player - the player to add
	 */
	public void addPlayer(Player player) {
		players.add(player);
	}

	public List<Player> getPlayers() {
		return players;
	}
	/**
	 * Calculate if the game can continue or not. If there aren't enough players online it will notify that the game
	 * can't continue.
	 * @return true if the game can continue, false otherwise.
	 */
	public boolean canContinue() {
		int i = 0;
		for(Player player : players) {
			if(player.isOnline()) {
				i++;
				if(i > 1) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Get the {@link Player} at the specific index.
	 * @param index - the position of the wanted player
	 * @return the selected player
	 */
	public Player getPlayer(int index) {
		return players.get(index);
	}
	/**
	 * Gets the number of players that can take part to the market phase. If a {@link Player} isn't online, he can't
	 * take part.
	 * @return the number of player that can take parts to the market
	 */
	public int marketPlayersNumber() {
		int n = 0;
		for(Player player : players) {
			if(player.isOnline()) {
				n++;
			}
		}
		return n;
	}
	/**
	 * Gets the number of players that are playing the current game.
	 * @return the number of player
	 */
	public int playersNumber() {
		return players.size();
	}
	
	@Override
	public String toString() {
		return players.toString();
	}
	
}
