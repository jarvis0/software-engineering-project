package it.polimi.ingsw.ps23.server.model.player;

import java.util.ArrayList;
import java.util.List;

public class PlayersSet {
	
	private List<Player> players;

	public PlayersSet() {
		players = new ArrayList<>();
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}

	public List<Player> getPlayers() {
		return players;
	}
	
	public Player getPlayer(int index) {
		return players.get(index);
	}
	
	public int numberOfPlayer() {
		int n = 0;
		for(Player player : players) {
			if(player.isOnline()) {
				n++;
			}
		}
		return n;
	}
	
	@Override
	public String toString() {
		return players.toString();
	}
	
}
