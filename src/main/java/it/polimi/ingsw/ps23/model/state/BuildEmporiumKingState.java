package it.polimi.ingsw.ps23.model.state;

import java.util.List;
import java.util.Map;
import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.HandDeck;
import it.polimi.ingsw.ps23.model.PoliticHandDeck;
import it.polimi.ingsw.ps23.model.actions.Action;
import it.polimi.ingsw.ps23.model.actions.BuildEmporiumKing;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.model.map.Council;
import it.polimi.ingsw.ps23.view.visitor.ViewVisitor;

public class BuildEmporiumKingState extends ActionState{
	
	Council kingCouncil;
	HandDeck availableCards;
	City kingPosition;
	HandDeck deck;
	Map<String, City> citiesMap;
	
	public BuildEmporiumKingState(String name) {
		super(name);
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		kingPosition = game.getKing().getPosition();
		kingCouncil = game.getKing().getCouncil();
		deck = game.getCurrentPlayer().getPoliticHandDeck();
		availableCards = ((PoliticHandDeck) game.getCurrentPlayer().getPoliticHandDeck()).getAvailableCards(kingCouncil);
		citiesMap = game.getGameMap().getCitiesMap();
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);	
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

	public Action createAction(List<String> removedCards, String arrivalCity) {
		return new BuildEmporiumKing(removedCards, citiesMap.get(arrivalCity), kingPosition);
	}
	
}