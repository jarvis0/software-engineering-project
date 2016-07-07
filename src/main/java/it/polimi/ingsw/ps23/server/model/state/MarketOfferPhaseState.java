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
/**
 * Provides methods to show all the info to create a {@link MarketObject}.
 * @author Mirco Manzoni
 *
 */
public class MarketOfferPhaseState extends MapUpdateState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6288171557528132844L;
	
	/**
	 * Returns the string of the current player.
	 * @return the name of the current player.
	 */
	public String getPlayerName() {
		return getCurrentPlayer().getName();
	}
	
	/**
	 * Returns the string of the current player politic hand deck.
	 * @return the set of the current player politic hand deck.
	 */
	public String getPoliticHandDeck() {
		return "Politic Hand Deck: " + getCurrentPlayer().getPoliticHandDeck().toString();
	}
	
	/**
	 * Returns the string of the current player permit hand deck.
	 * @return the set of the current player permit hand deck.
	 */
	public String getPermissionHandDeck() {
		return "Permission Hand Deck: " + getCurrentPlayer().getPermitHandDeck().toString();
	}
	
	/**
	 * Returns the string of the current player assistants number.
	 * @return the number of the current player's assistants.
	 */
	public String getAssistants() {
		  return "Assistants: " + getCurrentPlayer().getAssistants();
	}
	/**
	 * Calculate if the current {@link Player} can sell {@link PoliticCard} in the current market phase.
	 * @return true if can, false if can't
	 */
	public boolean canSellPoliticCards() {
		return getCurrentPlayer().getPoliticHandDeck().getHandSize() > 0;
	}
	
	/**
	 * Returns the size of the current player politic hand deck.
	 * @return the current player's cards number.
	 */
	public int getPoliticHandSize() {
		return getCurrentPlayer().getPoliticHandDeck().getHandSize();
	}
	/**
	 * Calculate if the current {@link Player} can sell {@link BusinessPermitTile} in the current market phase.
	 * @return true if can, false if can't
	 */
	public boolean canSellPermissionCards() {
		return getCurrentPlayer().getPermitHandDeck().getHandSize() > 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPermissionHandSize() {
		return getCurrentPlayer().getPermitHandDeck().getHandSize();
	}
	/**
	 * Calculate if the current {@link Player} can sell assistants in the current market phase.
	 * @return true if can, false if can't
	 */
	public boolean canSellAssistants() {
		return getCurrentPlayer().getAssistants() > 0;
	}
	
	private void checkMarketObject(List<String> chosenPoliticCards, List<Integer> chosenPermissionCards, int chosenAssistants, int cost) throws InvalidCardException, InvalidNumberOfAssistantException, InvalidCostException {
		((PoliticHandDeck)getCurrentPlayer().getPoliticHandDeck()).getCardsByName(chosenPoliticCards);
		for (int index : chosenPermissionCards) {
			getCurrentPlayer().getPermitHandDeck().getCardInPosition(index);
		}
		if(chosenAssistants < 0 || chosenAssistants > getCurrentPlayer().getAssistants()) {
			throw new InvalidNumberOfAssistantException();
		}
		if(cost < 0) {
			throw new InvalidCostException();
		}
	}
	/**
	 * Constructs the objects with all the parameters selected by the {@link Player}
	 * @param chosenPoliticCards - politic cards that the player want to sell
	 * @param chosenPermissionCards - permit tile that the player want to sell
	 * @param chosenAssistants - assistants that the player want to sell
	 * @param cost - cost that the player has chosen to his offer.
	 * @return the market object created
	 * @throws InvalidCardException if an invalid card has been selected
	 * @throws InvalidNumberOfAssistantException if an invalid number of assistant has been selected
	 * @throws InvalidCostException if an invalid cost has been selected
	 */
	public MarketObject createMarketObject(List<String> chosenPoliticCards, List<Integer> chosenPermissionCards, int chosenAssistants, int cost) throws InvalidCardException, InvalidNumberOfAssistantException, InvalidCostException {
		checkMarketObject(chosenPoliticCards, chosenPermissionCards, chosenAssistants, cost);
		return new MarketObject(getCurrentPlayer().getName(), chosenPermissionCards, chosenPoliticCards, chosenAssistants, cost);
	}
	
	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		setParameters(game);
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}
	
}
