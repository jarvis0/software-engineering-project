package it.polimi.ingsw.ps23.model.state;

import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.HandDeck;
import it.polimi.ingsw.ps23.model.PermissionHandDeck;
import it.polimi.ingsw.ps23.model.actions.BuildEmporiumPermitTile;
import it.polimi.ingsw.ps23.model.map.Card;
import it.polimi.ingsw.ps23.model.map.City;
import it.polimi.ingsw.ps23.view.visitor.ViewVisitor;

public class BuildEmporiumPermitTileState extends ActionState {

	HandDeck avaibleCards;
	Map<String, City> citiesMap;
	List<Card> permissionCards;
	
	public BuildEmporiumPermitTileState(String name) {
		super(name);
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		avaibleCards = ((PermissionHandDeck)game.getCurrentPlayer().getPermissionHandDeck()).getAvaiblePermissionCards();
		citiesMap = game.getGameMap().getCitiesMap();
		permissionCards = game.getCurrentPlayer().getPermissionHandDeck().getCards();
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);	
		
	}

	public String getAvaibleCards() {
		return avaibleCards.toString();
	}
	
	public String getChosenCard(int index){
		return avaibleCards.getCards().get(index).toString();
	}

	public BuildEmporiumPermitTile createAction(String chosenCity, int chosenCard) {
		return new BuildEmporiumPermitTile(citiesMap.get(chosenCity), chosenCard);
	}
	

}
