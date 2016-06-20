package it.polimi.ingsw.ps23.server.model.market;

import java.io.Serializable;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.player.Player;

public class MarketTransation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4809513366700722300L;
	private MarketObject requestedObject;
	private boolean hasPurchased;
	
	public MarketTransation() {
		hasPurchased = true;
	}
	
	public void notPurchased() {
		hasPurchased = false;
	}
	
	public void setRequestedObject(MarketObject requestedObject) {
		this.requestedObject = requestedObject;
	}
	
	public void doTransation(Game game) {
		if(hasPurchased) {
			Player seller = requestedObject.getPlayer();
			Player buyer = game.getCurrentPlayer();
			buyer.buyPoliticCards(requestedObject.getPoliticCards());
			seller.soldPoliticCards(requestedObject.getPoliticCards());
			buyer.buyPermissionCards(requestedObject.getPermissionCards());
			seller.soldPoliticCards(requestedObject.getPermissionCards());
			buyer.updateAssistants(requestedObject.getAssistants());
			seller.updateAssistants(- requestedObject.getAssistants());
			buyer.updateCoins(- requestedObject.getCost());
			seller.updateCoins(requestedObject.getCost());
			game.getMarket().remove(requestedObject);
		}
	}
	
}
