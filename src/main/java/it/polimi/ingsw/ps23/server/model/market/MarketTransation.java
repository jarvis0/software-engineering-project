package it.polimi.ingsw.ps23.server.model.market;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;

public class MarketTransation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4809513366700722300L;
	private MarketObject requestedObject;
	private boolean hasPurchased;
	private Map<String, Player> playersMap;
	private List<Card> politicCards;
	private List<Card> permissionCards;
	
	public MarketTransation() {
		hasPurchased = true;
		playersMap = new HashMap<>();
		politicCards = new ArrayList<>();
		permissionCards = new ArrayList<>();
	}
	
	public void notPurchased() {
		hasPurchased = false;
	}
	
	public void setRequestedObject(MarketObject requestedObject) {
		this.requestedObject = requestedObject;
	}
	
	private void createPlayersMap(Game game) {
		for(Player player : game.getGamePlayersSet().getPlayers()) {
			playersMap.put(player.getName(), player);
		}
	}
	
	private void createListCard() throws InvalidCardException {
	politicCards.addAll(((PoliticHandDeck)playersMap.get(requestedObject.getPlayer()).getPoliticHandDeck()).getCardsByName(requestedObject.getPoliticCards()));
		for (int index : requestedObject.getPermissionCards()) {
			permissionCards.add(playersMap.get(requestedObject.getPlayer()).getPermitHandDeck().getCardInPosition(index));
		}
	}
	
	public void doTransation(Game game) throws InvalidCardException {
		if(hasPurchased) {
			createPlayersMap(game);
			createListCard();
			Player seller = playersMap.get(requestedObject.getPlayer());
			Player buyer = game.getCurrentPlayer();
			buyer.buyPoliticCards(politicCards);
			seller.sellPoliticCards(politicCards);
			buyer.buyPermitCards(permissionCards);
			seller.sellPermitCards(permissionCards);
			buyer.updateAssistants(requestedObject.getAssistants());
			seller.updateAssistants(- requestedObject.getAssistants());
			buyer.updateCoins(- requestedObject.getCost());
			seller.updateCoins(requestedObject.getCost());
			game.getMarket().remove(requestedObject);
		}
	}
	
}
