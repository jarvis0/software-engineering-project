package it.polimi.ingsw.ps23.server.model.state;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.market.Market;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.market.MarketTransaction;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;
/**
 * Provides methods to create a {@link MarketTransaction}.
 * @author Mirco Manzoni
 *
 */
public class MarketBuyPhaseState extends State {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7921523727192618428L;
	private Market market;
	private Player currentPlayer;

	/**
	 * @return the current player name.
	 */
	public String getPlayerName() {
		return currentPlayer.getName();
	}
	/**
	 * Calculates if the current {@link Player} can buy in the current market phase.
	 * @return true if can, false if can't
	 */
	public boolean canBuy() {
		for(MarketObject marketObject : market.getMarketObject()) {
			if(!marketObject.getPlayer().equals(currentPlayer.getName()) && marketObject.getCost() <= currentPlayer.getCoins()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return a CLI print for all available market offers.
	 */
	public String getAvaiableOffers() {
		StringBuilder avaiableOffers = new StringBuilder();
		for(MarketObject marketObject : market.getMarketObject()) {
			if(!marketObject.getPlayer().equals(currentPlayer.getName()) && marketObject.getCost() <= currentPlayer.getCoins()) {
				avaiableOffers.append("\n" + marketObject.toString());
			}
		}
		return new String() + avaiableOffers;
	}
	/**
	 * Creates the {@link MarketTransaction} when the current {@link Player} can't buy. 
	 * @return the market transaction created
	 */
	public MarketTransaction createTransation() {
		MarketTransaction marketTransaction = new MarketTransaction();
		marketTransaction.notPurchased();
		return marketTransaction;
	}
	/**
	 * Creates the {@link MarketTransaction} when the current {@link Player} can buy.
	 * @param selectedItem - the number of the offer selected
	 * @return the market transaction created
	 */
	public MarketTransaction createTransation(int selectedItem) {
		MarketTransaction marketTransaction = new MarketTransaction();
		int i = 0;
		for(MarketObject marketObject : market.getMarketObject()) {
			if(!marketObject.getPlayer().equals(currentPlayer.getName()) && marketObject.getCost() <= currentPlayer.getCoins()) {
				if(i == selectedItem) {
					marketTransaction.setRequestedObject(marketObject);
					return marketTransaction;
				}
				else {
					i++;
				}
			}
		}
		marketTransaction.notPurchased();
		return marketTransaction;
	}
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		market = game.getMarket();	
		currentPlayer = game.getCurrentPlayer();
	}
	
	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}
	
}
