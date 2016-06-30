package it.polimi.ingsw.ps23.server.model.state;

import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCostException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidNumberOfAssistantException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.market.MarketObject;
import it.polimi.ingsw.ps23.server.model.player.Player;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class MarketOfferPhaseState extends State {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6288171557528132844L;
	private Player currentPlayer;
	
	public String getPlayerName() {
		return currentPlayer.getName();
	}
	
	public String getPoliticHandDeck() {
		return "Politic Hand Deck: " + currentPlayer.getPoliticHandDeck().toString();
	}
	
	public String getPermissionHandDeck() {
		return "Permission Hand Deck: " + currentPlayer.getPermitHandDeck().toString();
	}
	
	public String getAssistants() {
		  return "Assistants: " + currentPlayer.getAssistants();
	}
	
	public boolean canSellPoliticCards() {
		return currentPlayer.getPoliticHandDeck().getHandSize() > 0;
	}
	
	public int getPoliticHandSize() {
		return currentPlayer.getPoliticHandDeck().getHandSize();
	}
	
	public boolean canSellPermissionCards() {
		return currentPlayer.getPermitHandDeck().getHandSize() > 0;
	}
	
	public int getPermissionHandSize() {
		return currentPlayer.getPermitHandDeck().getHandSize();
	}
	
	public boolean canSellAssistants() {
		return currentPlayer.getAssistants() > 0;
	}
	
	private void checkMarketObject(List<String> chosenPoliticCards, List<Integer> chosenPermissionCards, int chosenAssistants, int cost) throws InvalidCardException, InvalidNumberOfAssistantException, InvalidCostException {
		for (String card : chosenPoliticCards) {
			((PoliticHandDeck)currentPlayer.getPoliticHandDeck()).getCardFromName(card);
		}
		for (int index : chosenPermissionCards) {
			currentPlayer.getPermitHandDeck().getCardInPosition(index);
		}
		if(chosenAssistants < 0 || chosenAssistants > currentPlayer.getAssistants()) {
			throw new InvalidNumberOfAssistantException();
		}
		if(cost <= 0) {
			throw new InvalidCostException();
		}
	}

	public MarketObject createMarketObject(List<String> chosenPoliticCards, List<Integer> chosenPermissionCards, int chosenAssistants, int cost) throws InvalidCardException, InvalidNumberOfAssistantException, InvalidCostException {
		checkMarketObject(chosenPoliticCards, chosenPermissionCards, chosenAssistants, cost);
		return new MarketObject(currentPlayer.getName(), chosenPermissionCards, chosenPoliticCards, chosenAssistants, cost);
	}
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		currentPlayer = game.getCurrentPlayer();
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}
	
}
