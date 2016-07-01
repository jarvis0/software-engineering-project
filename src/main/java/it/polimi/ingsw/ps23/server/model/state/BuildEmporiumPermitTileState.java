package it.polimi.ingsw.ps23.server.model.state;

import it.polimi.ingsw.ps23.server.commons.exceptions.IllegalActionSelectedException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.BuildEmporiumPermitTile;
import it.polimi.ingsw.ps23.server.model.player.HandDeck;
import it.polimi.ingsw.ps23.server.model.player.PermitHandDeck;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class BuildEmporiumPermitTileState extends MainActionState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8070870675842800922L;
	private HandDeck availableCards;
	
	BuildEmporiumPermitTileState(String name) {
		super(name);
	}

	public String getAvaibleCards() throws IllegalActionSelectedException {
		if (availableCards.getHandSize() == 0) {
			throw new IllegalActionSelectedException();
		}
		return availableCards.toString();
	}
	
	public String getChosenCard(int index) throws InvalidCardException {
		if(index >= availableCards.getHandSize() || index < 0) {
			throw new InvalidCardException();
		}
		return availableCards.getCards().get(index).toString();
	}

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
