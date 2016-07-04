package it.polimi.ingsw.ps23.server.model.state;

import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.IllegalActionSelectedException;
import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.BuildEmporiumKing;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.player.HandDeck;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;
/**
 * Provides methods to get all info to create {@link BuildEmporiumKing} action.
 * @author Alessandro Erba, Mirco Manzoni
 *
 */
public class BuildEmporiumKingState extends MainActionState {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8190515657408088895L;
	private Council kingCouncil;
	private HandDeck availableCards;
	private City kingPosition;
	
	BuildEmporiumKingState(String name) {
		super(name);
	}
	
	public int getPoliticHandSize() {
		if(availableCards.getHandSize() > 4){
			return 4;
		}
		return availableCards.getHandSize();
	}
	
	public String getAvailableCards() {
		return availableCards.toString();
	}
	
	public int getAvailableCardsNumber() throws IllegalActionSelectedException {
		if(availableCards.getHandSize() == 0) {
			throw new IllegalActionSelectedException();
		}
		return availableCards.getHandSize();
	}

	public String getKingPosition() {
		return kingPosition.toString();
	}
	
	private void checkCards(List<String> removedPoliticCards) throws InvalidCardException {
		String council = kingCouncil.toString();
		for (String string : removedPoliticCards) {
			if (council.contains(string) || "multi".equals(string)) {
				council = council.replaceFirst(string, "");
			}
			else {
				throw new InvalidCardException();
			}
		}
	}
	/**
	 * Create the {@link BuildEmporiumKingState} action with all the parameters selected.
	 * @param removedCards - the politic card selected
	 * @param arrivalCity - the city where the {@link King} will put
	 * @return the created action
	 * @throws InvalidCardException if an invalid card has been selected
	 */
	public Action createAction(List<String> removedCards, String arrivalCity) throws InvalidCardException {
		checkCards(removedCards);
		return new BuildEmporiumKing(removedCards, arrivalCity);
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		kingPosition = game.getKing().getPosition();
		kingCouncil = game.getKing().getCouncil();
		availableCards = ((PoliticHandDeck) game.getCurrentPlayer().getPoliticHandDeck()).getAvailableCards(kingCouncil);
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);	
	}
	
}
