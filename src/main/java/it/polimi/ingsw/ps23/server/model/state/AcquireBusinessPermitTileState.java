package it.polimi.ingsw.ps23.server.model.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCouncilException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.AcquireBusinessPermitTile;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.player.HandDeck;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;
/**
 * Provides methods to take all parameters to create {@link AcquireBusinessPermitTile} action.
 * @author Alessandro Erba, Mirco Manzoni
 *
 */
public class AcquireBusinessPermitTileState extends MainActionState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6409535756165340260L;

	private static final int MAX_CARDS_NUMBER = 4;
	
	private HandDeck politicHandDeck;
	private Map<String, Region> regionsMap;
	
	AcquireBusinessPermitTileState(String name) {
		super(name);
		regionsMap = new HashMap<>();
	}

	/**
	 * @return a CLI print of the current player politic hand cards.
	 */
	public String getPoliticHandDeck() {
		return politicHandDeck.toString();
	}
	
	/**
	 * Returns the number of current player's politic hand cards, if he has more than 4 politic cards,
	 * this method returns exactly 4.
	 * @return the size of the current player politic hand deck size.
	 */
	public int getPoliticHandSize() {
		if(politicHandDeck.getHandSize() > 4){
			return 4;
		}
		return politicHandDeck.getHandSize();
	}
	
	/**
	 * @return a CLI print of every game map council.
	 */
	public String getCouncilsMap() {
		return regionsMap.toString();
	}
	
	private void checkCards(String chosenCouncil, List<String> removedPoliticCards) throws InvalidCardException {
		String council = ((GroupRegionalCity)regionsMap.get(chosenCouncil)).getCouncil().toString();
		for(String string : removedPoliticCards) {
			if(council.contains(string) || "multi".equals(string)) {
				council = council.replaceFirst(string, "");
			}
			else {
				throw new InvalidCardException();
			}
		}
	}
	/**
	 * Counts the available {@link PoliticCard} of the current {@link Player} to satisfy the selected council.
	 * @param chosenCouncil - the selected council
	 * @return new amount of cards that satisfy the council
	 * @throws InvalidCouncilException if an invalid council has been selected
	 */
	public int getAvailablePoliticCardsNumber(String chosenCouncil) throws InvalidCouncilException {
		if(regionsMap.get(chosenCouncil) == null) {
			throw new InvalidCouncilException();
		}
		politicHandDeck = ((PoliticHandDeck)politicHandDeck).getAvailableCards(((GroupRegionalCity)regionsMap.get(chosenCouncil)).getCouncil());
		if(politicHandDeck.getHandSize() > MAX_CARDS_NUMBER) {
			return MAX_CARDS_NUMBER;
		}
		return politicHandDeck.getHandSize();
	}
	/**
	 * Gets the available {@link PermitTile} to satisfy the selected council.
	 * @param chosenCouncil - the selected council
	 * @return the list of cards in string format
	 */
	public String getAvailablePermitTile(String chosenCouncil) {
		return ((GroupRegionalCity)regionsMap.get(chosenCouncil)).getPermitTilesUp().toString();
	}
	/**
	 * Creates the selected action with the selected parameters.
	 * @param chosenCouncil - the selected council
	 * @param removedPoliticCards - the selected politic cards to satisfy the council
	 * @param chosenPermissionCard - the selected permit tile to pick
	 * @return the created action
	 * @throws InvalidCardException if an invalid card is selected
	 */
	public Action createAction(String chosenCouncil, List<String> removedPoliticCards, int chosenPermissionCard) throws InvalidCardException {
		checkCards(chosenCouncil, removedPoliticCards);
		return new AcquireBusinessPermitTile(removedPoliticCards, chosenCouncil, chosenPermissionCard);
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		politicHandDeck = new PoliticHandDeck(game.getCurrentPlayer().getPoliticHandDeck().getCards());
		regionsMap = game.getGameMap().getRegionMap();
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}

}
