package it.polimi.ingsw.ps23.server.model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayersSet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2043417363819650294L;
	private List<Player> players;

	public PlayersSet() {
		players = new ArrayList<>();
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}

	// usato dal market e x stamapre i giocatori
	public List<Player> getPlayers() {
		return players;
	}
	
	public Player getPlayer(int index) {
		return players.get(index);
	}
	
	public int marketPlayersNumber() {
		int n = 0;
		for(Player player : players) {
			if(player.isOnline()) {
				n++;
			}
		}
		return n;
	}
	
	public int playersNumber() {
		return players.size();
	}
	
	@Override
	public String toString() {
		return players.toString();
	}
	
}
