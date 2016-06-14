package it.polimi.ingsw.ps23.server.model.market;

import java.util.Collections;

import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PlayersSet;

class MarketPlayersSet extends PlayersSet {
	
	MarketPlayersSet() {
		super();
	}
	
	void shufflePlayer() {
		Collections.shuffle(getPlayers());
	}
	
	boolean isEmpty() {
		return getPlayers().isEmpty();
	}
	
	Player getCurrentPlayer() {
		return getPlayers().remove(getPlayers().size() - 1);
	}

}
