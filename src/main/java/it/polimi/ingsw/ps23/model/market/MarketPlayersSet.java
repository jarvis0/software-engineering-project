package it.polimi.ingsw.ps23.model.market;

import java.util.Collections;

import it.polimi.ingsw.ps23.model.GamePlayersSet;
import it.polimi.ingsw.ps23.model.Player;

public class MarketPlayersSet extends GamePlayersSet{
	
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
