package it.polimi.ingsw.ps23.model.market;

import java.util.List;

import it.polimi.ingsw.ps23.model.market.MarketObject;

public class Market {

	private List<MarketObject> marketObjects;
	
	
	public List<MarketObject> getMarketObject() {
		return marketObjects;
	}
	
	public void addMarketObject(MarketObject marketObject) {
		marketObjects.add(marketObject);
	}
	
	public int sellObjects() {
		return marketObjects.size();
	}
	
}
