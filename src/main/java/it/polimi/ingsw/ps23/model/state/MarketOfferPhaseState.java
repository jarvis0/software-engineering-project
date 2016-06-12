package it.polimi.ingsw.ps23.model.state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.Player;
import it.polimi.ingsw.ps23.model.PoliticHandDeck;
import it.polimi.ingsw.ps23.model.market.MarketObject;
import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.view.ViewVisitor;

public class MarketOfferPhaseState implements State {

	private Player currentPlayer;
	
	private Logger logger;
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		currentPlayer = game.getCurrentPlayer();
	}
	
	public String getPlayerName() {
		return currentPlayer.getName();
	}
	
	public String getPoliticHandDeck() {
		return "Politic Hand Deck: " + currentPlayer.getPoliticHandDeck().toString();
	}
	
	public String getPermissionHandDeck() {
		return "Permission Hand Deck: " + currentPlayer.getPermissionHandDeck().toString();
	}
	
	public String getAssistants() {
		  return "Assistants: " + currentPlayer.getAssistants();
	}
	
	public boolean canSellPoliticCards() {
		return currentPlayer.getPoliticHandDeck().getHandSize() > 0;
	}
	
	public boolean canSellPermissionCards() {
		return currentPlayer.getPermissionHandDeck().getHandSize() > 0;
	}
	
	public boolean canSellAssistants() {
		return currentPlayer.getAssistants() > 0;
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}
	
	public MarketObject createMarketObject(List<String> chosenPoliticCards, List<Integer> chosenPermissionCards, int chosenAssistants, int cost) {
		logger = Logger.getLogger(this.getClass().getName()); //TODO se si mette il costruttore, spostare questa istruzione lì
		List<Card> politicCards = new ArrayList<>();
		List<Card> permissionCards = new ArrayList<>();		
		for (String card : chosenPoliticCards) {
			try {
				politicCards.add(((PoliticHandDeck)currentPlayer.getPoliticHandDeck()).getCardFromName(card));
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Cannot find out chosen object.", e);
			}
		}
		for (int index : chosenPermissionCards) {
			permissionCards.add(currentPlayer.getPermissionHandDeck().getCardInPosition(index));
		}
		return new MarketObject(currentPlayer, permissionCards, politicCards, chosenAssistants, cost);
	}

}
