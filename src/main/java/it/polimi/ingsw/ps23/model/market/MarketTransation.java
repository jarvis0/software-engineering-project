package it.polimi.ingsw.ps23.model.market;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.market.MarketObject;

public class MarketTransation {

	private MarketObject requestedObject;
	private boolean hasPurchase;
	
	public MarketTransation() {
		hasPurchase = true;
	}
	
	public void notPurchased() {
		hasPurchase = false;
	}
	
	public void setRequestedObject(MarketObject requestedObject) {
		this.requestedObject = requestedObject;
	}
	
	public void doTransation(Game game) {
		if(hasPurchase) {
			Player seller = requestedObject.getPlayer();
			Player buyer = game.getCurrentPlayer();
			buyer.buyPoliticCards(requestedObject.getPoliticCards());
			seller.soldPoliticCards(requestedObject.getPoliticCards());
			buyer.buyPermissionCards(requestedObject.getPermissionCards());
			seller.soldPoliticCards(requestedObject.getPermissionCards());
			try {
				buyer.updateAssistants(requestedObject.getAssistants());
				seller.updateAssistants(- requestedObject.getAssistants());
				buyer.updateCoins(- requestedObject.getCost());
				seller.updateCoins(requestedObject.getCost());
			} catch (InsufficientResourcesException e) {
				e.printStackTrace();
			}
		}
	}
}
