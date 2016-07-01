package it.polimi.ingsw.ps23.server.model.market;

import java.io.Serializable;
import java.util.List;
/**
 * It is the container of all objects that a player want to sell.
 * @author Mirco Manzoni
 *
 */
public class MarketObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7572250712520487859L;
	private String player;
	private List<Integer> permissionCards;
	private List<String> politicCards;
	private int assistants;
	private int cost;
	/**
	 * Create an object that contains all the obejcts that the current {@link Player} want to sell.
	 * @param player - the seller player
	 * @param permissionCards - chosen permission cards
	 * @param politicCards - chosen politic cards
	 * @param assistants - number of chosen assistant
	 * @param cost - cost of the offer
	 */
	public MarketObject(String player, List<Integer> permissionCards, List<String> politicCards, int assistants, int cost) {
		this.player = player;
		this.permissionCards = permissionCards;
		this.politicCards = politicCards;
		this.assistants = assistants;
		this.cost = cost;
	}

	public String getPlayer() {
		return player;
	}

	List<Integer> getPermissionCards() {
		return permissionCards;
	}

	List<String> getPoliticCards() {
		return politicCards;
	}

	int getAssistants() {
		return assistants;
	}

	public int getCost() {
		return cost;
	}
	
	@Override
	public String toString() {
		return player + " price: " + cost + " politicCards: " + politicCards + " permissionCards: " + permissionCards + " assistants: " + assistants;
	}
	
}