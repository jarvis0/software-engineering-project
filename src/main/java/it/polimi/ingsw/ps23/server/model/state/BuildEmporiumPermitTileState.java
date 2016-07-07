package it.polimi.ingsw.ps23.server.model.state;

import it.polimi.ingsw.ps23.server.commons.exceptions.IllegalActionSelectedException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.BuildEmporiumPermitTile;
import it.polimi.ingsw.ps23.server.model.player.HandDeck;
import it.polimi.ingsw.ps23.server.model.player.PermitHandDeck;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;
/**
 * Provides methods to get all info to create {@link BuildEmporiumPermitTile} action.
 * @author Alessandro Erba, Mirco Manzoni
 *
 */
public class BuildEmporiumPermitTileState extends MainActionState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8070870675842800922L;
	private HandDeck availableCards;
	
	BuildEmporiumPermitTileState(String name) {
		super(name);
	}

	/**
	 * @return a string representation of all available cards in a deck.
	 * @throws IllegalActionSelectedException if the player cannot perform this action
	 * due to the fact he has not any card in his hand deck.
	 */
	public String getAvailableCards() throws IllegalActionSelectedException {
		if (availableCards.getHandSize() == 0) {
			throw new IllegalActionSelectedException();
		}
		return availableCards.toString();
	}
	/**
	 * Finds the {@link Card} at the specific position in the pool.
	 * @param index - the position of the card
	 * @return the string of the selected card
	 * @throws InvalidCardException if an invalid card has been selected
	 */
	public String getChosenCard(int index) throws InvalidCardException {
		if(index >= availableCards.getHandSize() || index < 0) {
			throw new InvalidCardException();
		}
		return availableCards.getCards().get(index).toString();
	}
	/**
	 * Create the {@link builEmporiumPermitTile} action with all parametres required.
	 * @param chosenCity - the chosen city where to build
	 * @param chosenCard - the card selected
	 * @return the created action
	 */
	public Action createAction(String chosenCity, int chosenCard) {
		return new BuildEmporiumPermitTile(chosenCity, chosenCard);
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		availableCards = ((PermitHandDeck)game.getCurrentPlayer().getPermitHandDeck()).getAvaiblePermissionCards();
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);	
	}

}
