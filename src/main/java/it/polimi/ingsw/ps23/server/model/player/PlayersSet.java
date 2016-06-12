package it.polimi.ingsw.ps23.server.model.player;

import java.util.ArrayList;
import java.util.List;

//forse meglio HashMap ?
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
		return players.size();
	}
	
	@Override
	public String toString() {
		return players.toString();
	}
	
}
