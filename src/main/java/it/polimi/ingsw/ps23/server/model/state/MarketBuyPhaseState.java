package it.polimi.ingsw.ps23.server.model.state;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.market.Market;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.market.MarketTransaction;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class MarketBuyPhaseState extends State {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7921523727192618428L;
	private Market market;
	private Player currentPlayer;

	public String getPlayerName() {
		return currentPlayer.getName();
	}
	
	public boolean canBuy() {
		for(MarketObject marketObject : market.getMarketObject()) {
			if(!marketObject.getPlayer().equals(currentPlayer.getName()) && marketObject.getCost() <= currentPlayer.getCoins()) {
				return true;
			}
		}
		return false;
	}
	
	public String getAvaiableOffers() {
		StringBuilder avaiableOffers = new StringBuilder();
		for(MarketObject marketObject : market.getMarketObject()) {
			if(!marketObject.getPlayer().equals(currentPlayer.getName()) && marketObject.getCost() <= currentPlayer.getCoins()) {
				avaiableOffers.append("\n" + marketObject.toString());
			}
		}
		return new String() + avaiableOffers;
	}

	public MarketTransaction createTransation() {
		MarketTransaction marketTransaction = new MarketTransaction();
		marketTransaction.notPurchased();
		return marketTransaction;
	}
	
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
