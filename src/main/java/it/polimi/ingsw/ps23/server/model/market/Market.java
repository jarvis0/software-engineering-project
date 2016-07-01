package it.polimi.ingsw.ps23.server.model.market;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PlayersSet;
/**
 * Manage the market phase system shuffling and selecting players and save all the {@link MarketObject} chosen
 * by players
 * @author Mirco Manzoni
 *
 */
public class Market implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 371156005758958227L;
	private List<MarketObject> marketObjectSet;
	private PlayersSet playersSet;
	/**
	 * Initialize all variables to the default value and shuffle players for the second part of the market phase
	 * @param playersSet - all the players in game
	 */
	public Market(PlayersSet playersSet) {
		marketObjectSet = new ArrayList<>();
		this.playersSet = new MarketPlayersSet();
		for(Player player : playersSet.getPlayers()) {
			this.playersSet.addPlayer(player);
		}
		((MarketPlayersSet)this.playersSet).shufflePlayers();
	}
		
	public List<MarketObject> getMarketObject() {
		return marketObjectSet;
	}
	/**
	 * Add the selected market object to the pool of object that players want to sell
	 * @param marketObject - the offer selected from the current {@link Player}
	 */
	public void addMarketObject(MarketObject marketObject) {
		marketObjectSet.add(marketObject);
	}
	/**
	 * Count all the on sale objects
	 * @return number of items on sale
	 */
	public int forSaleObjectsSize() {
		return marketObjectSet.size();
	}
	/**
	 * Calculate if the market buy phase state can continue
	 * @return true if can continue, false if can't
	 */
	public boolean canContinueMarket() {
		return !((MarketPlayersSet)playersSet).isEmpty();
	}
	/**
	 * Select the next {@link Player} for the market buy phase. If a {@link Player} is not online, he will be not selected
	 * @return - the player selected.
	 */
	public Player selectPlayer() {
		Player nextPlayer;
		do {
			nextPlayer = ((MarketPlayersSet)playersSet).getCurrentPlayer();
		} while(!nextPlayer.isOnline());
		return nextPlayer;
	}
	/**
	 * Remove the used {@link MarketObject} from the pool of objects.
	 * @param requestedObject - object to remove.
	 */
	public void remove(MarketObject requestedObject) {
		marketObjectSet.remove(requestedObject);
	}
}
