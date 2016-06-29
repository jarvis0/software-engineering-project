package it.polimi.ingsw.ps23.server.model.state;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.market.Market;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.market.MarketTransation;
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

	public MarketTransation createTransation() {
		MarketTransation marketTransation = new MarketTransation();
		marketTransation.notPurchased();
		return marketTransation;
	}
	
	public MarketTransation createTransation(int selectedItem) {
		MarketTransation marketTransation = new MarketTransation();
		int i = 0;
		for(MarketObject marketObject : market.getMarketObject()) {
			if(!marketObject.getPlayer().equals(currentPlayer.getName()) && marketObject.getCost() <= currentPlayer.getCoins()) {
				if(i == selectedItem) {
					marketTransation.setRequestedObject(marketObject);
					return marketTransation;
				}
				else {
					i++;
				}
			}
		}
		marketTransation.notPurchased();
		return marketTransation;
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
