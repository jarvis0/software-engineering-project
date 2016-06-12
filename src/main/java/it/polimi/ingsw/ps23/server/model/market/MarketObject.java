package it.polimi.ingsw.ps23.server.model.market;

import java.util.List;

import it.polimi.ingsw.ps23.server.model.map.Card;
import it.polimi.ingsw.ps23.server.model.player.Player;

public class MarketObject {
	
	private Player player;
	private List<Card> permissionCards;
	private List<Card> politicCards;
	private int assistants;
	private int cost;
	
	public MarketObject(Player player, List<Card> permissionCards, List<Card> politicCards, int assistants, int cost) {
		this.player = player;
		this.permissionCards = permissionCards;
		this.politicCards = politicCards;
		this.assistants = assistants;
		this.cost = cost;
	}

	public Player getPlayer() {
		return player;
	}

	public List<Card> getPermissionCards() {
		return permissionCards;
	}

	public List<Card> getPoliticCards() {
		return politicCards;
	}

	public int getAssistants() {
		return assistants;
	}

	public int getCost() {
		return cost;
	}
	
	@Override
	public String toString() {
		return player.getName() + " price: " + cost + " politicCards: " + politicCards + " permissionCards: " + permissionCards + " assistants: " + assistants;
	}
}
