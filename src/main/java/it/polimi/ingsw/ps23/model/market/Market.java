package it.polimi.ingsw.ps23.model.market;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.model.GamePlayersSet;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.market.MarketObject;

public class Market {

	private List<MarketObject> marketObjectSet;
	private GamePlayersSet gamePlayersSet;
	
	public Market(GamePlayersSet gamePlayersSet) {
		marketObjectSet = new ArrayList<>();
		this.gamePlayersSet = new MarketPlayersSet();
		for(Player player : gamePlayersSet.getPlayers()) {
			this.gamePlayersSet.addPlayer(player);
		}
		((MarketPlayersSet)this.gamePlayersSet).shufflePlayer();
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
		return !((MarketPlayersSet)gamePlayersSet).isEmpty();
	}
	
	public Player selectPlayer() {
		return ((MarketPlayersSet)gamePlayersSet).getCurrentPlayer();
	}
}
