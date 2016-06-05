package it.polimi.ingsw.ps23.model.actions;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.TurnHandler;
import it.polimi.ingsw.ps23.model.market.MarketObject;

public class MarketAction extends Action {

	private MarketObject requestedMarket;
	
	public MarketAction(MarketObject requestedMarket) {
		this.requestedMarket = requestedMarket;
	}
	
	@Override
	public void doAction(Game game, TurnHandler turnHandler) {
		//rimuovere gli oggetti da un giocatore e metterlo all'altro
		
	}

}
