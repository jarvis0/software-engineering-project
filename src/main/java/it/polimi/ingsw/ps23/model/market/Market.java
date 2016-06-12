package it.polimi.ingsw.ps23.model.market;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.market.MarketObject;
import it.polimi.ingsw.ps23.model.player.Player;
import it.polimi.ingsw.ps23.model.player.PlayersSet;

public class Market {

	private List<MarketObject> marketObjectSet;
	private PlayersSet playersSet;
	
	public Market(PlayersSet playersSet) {
		marketObjectSet = new ArrayList<>();
		this.playersSet = new MarketPlayersSet();
		for(Player player : playersSet.getPlayers()) {
			this.playersSet.addPlayer(player);
		}
		((MarketPlayersSet)this.playersSet).shufflePlayer();
	}
		
	public List<MarketObject> getMarketObject() {
		return marketObjectSet;
	}
	
	public void addMarketObject(MarketObject marketObject) {
		marketObjectSet.add(marketObject);
	}
	
	public int sellObjects() {
		return marketObjectSet.size();
	}
	
	public boolean continueMarket() {
		return !((MarketPlayersSet)playersSet).isEmpty();
	}
	
	public Player selectPlayer() {
		return ((MarketPlayersSet)playersSet).getCurrentPlayer();
	}

	public void remove(MarketObject requestedObject) {
		marketObjectSet.remove(requestedObject);
	}
}
