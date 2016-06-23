package it.polimi.ingsw.ps23.server.model.state;

import java.util.List;

import it.polimi.ingsw.ps23.server.commons.exceptions.InvalidCardException;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.BuildEmporiumKing;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.map.regions.Council;
import it.polimi.ingsw.ps23.server.model.player.HandDeck;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class BuildEmporiumKingState extends ActionState {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8190515657408088895L;
	private Council kingCouncil;
	private HandDeck availableCards;
	private City kingPosition;
	private HandDeck deck;
	
	BuildEmporiumKingState(String name) {
		super(name);
	}
	
	public int getPoliticHandSize() {
		if(deck.getHandSize() > 4){
			return 4;
		}
		return deck.getHandSize();
	}

	public String getDeck() {
		return deck.toString();
	}
	
	public String getAvailableCards() {
		return availableCards.toString();
	}
	
	public int getAvailableCardsNumber() {
		return availableCards.getHandSize();
	}

	public String getKingPosition() {
		return kingPosition.toString();
	}
	
	private void checkCards(List<String> removedPoliticCards) throws InvalidCardException {
		String council = kingCouncil.toString();
		for (String string : removedPoliticCards) {
			if (council.contains(string) || string.equals("multi")) {
				council = council.replaceFirst(string, "");
			}
			else {
				throw new InvalidCardException();
			}
		}
	}

	public Action createAction(List<String> removedCards, String arrivalCity) throws InvalidCardException {
		checkCards(removedCards);
		return new BuildEmporiumKing(removedCards, arrivalCity);
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		kingPosition = game.getKing().getPosition();
		kingCouncil = game.getKing().getCouncil();
		deck = game.getCurrentPlayer().getPoliticHandDeck();
		availableCards = ((PoliticHandDeck) game.getCurrentPlayer().getPoliticHandDeck()).getAvailableCards(kingCouncil);
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);	
	}
	
}
