package it.polimi.ingsw.ps23.model;

import java.util.ArrayList;
import java.util.List;

public class GamePlayers {
	
	private List<Player> players;

	public GamePlayers() {
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
	
	public String toString() {
		return players.toString();
	}
	
}
