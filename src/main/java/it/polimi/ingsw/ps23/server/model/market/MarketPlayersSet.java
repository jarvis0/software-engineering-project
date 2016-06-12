package it.polimi.ingsw.ps23.server.model.market;

import java.util.Collections;

import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PlayersSet;

public class MarketPlayersSet extends PlayersSet {
	
	public MarketPlayersSet() {
		super();
	}
	
	public void shufflePlayer() {
		Collections.shuffle(getPlayers());
	}
	
	public boolean isEmpty() {
		return getPlayers().isEmpty();
	}
	
	public Player getCurrentPlayer() {
		return getPlayers().remove(getPlayers().size() - 1);
	}

}