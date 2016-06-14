package it.polimi.ingsw.ps23.server.model.market;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InsufficientResourcesException;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.player.Player;

public class MarketTransation {

	private MarketObject requestedObject;
	private boolean hasPurchased;
	
	private Logger logger;
	
	public MarketTransation() {
		hasPurchased = true;
		logger = Logger.getLogger(this.getClass().getName());
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
			try {
				buyer.updateAssistants(requestedObject.getAssistants());
				seller.updateAssistants(- requestedObject.getAssistants());
				buyer.updateCoins(- requestedObject.getCost());
				seller.updateCoins(requestedObject.getCost());
			} catch (InsufficientResourcesException e) {
				logger.log(Level.SEVERE, "Either insufficient player assistants or coins.", e);
			}
			game.getMarket().remove(requestedObject);
		}
	}
	
}
