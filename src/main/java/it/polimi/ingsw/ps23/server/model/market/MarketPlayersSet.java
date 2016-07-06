package it.polimi.ingsw.ps23.server.model.market;

import java.util.Collections;

import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PlayersSet;

class MarketPlayersSet extends PlayersSet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5011596314294155431L;

	MarketPlayersSet() {
		super();
	}
	
	void shufflePlayers() {
		Collections.shuffle(getPlayers());
	}
	
	boolean isEmpty() {
		for(Player player : getPlayers()) {
			if(player.isOnline()) {
				return false;
			}
		}
		return true;
	}
	
	Player getCurrentPlayer() {
		return getPlayers().remove(getPlayers().size() - 1);
	}

}
