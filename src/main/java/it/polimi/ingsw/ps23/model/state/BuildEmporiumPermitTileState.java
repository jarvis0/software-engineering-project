package it.polimi.ingsw.ps23.model.state;

import java.util.Map;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.actions.Action;
import it.polimi.ingsw.ps23.model.actions.BuildEmporiumPermitTile;
import it.polimi.ingsw.ps23.model.map.regions.City;
import it.polimi.ingsw.ps23.model.player.HandDeck;
import it.polimi.ingsw.ps23.model.player.PermissionHandDeck;
import it.polimi.ingsw.ps23.view.ViewVisitor;

public class BuildEmporiumPermitTileState extends ActionState {

	private HandDeck availableCards;
	private Map<String, City> citiesMap;
	
	public BuildEmporiumPermitTileState(String name) {
		super(name);
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		availableCards = ((PermissionHandDeck)game.getCurrentPlayer().getPermissionHandDeck()).getAvaiblePermissionCards();
		citiesMap = game.getGameMap().getCitiesMap();
	}

	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);	
		
	}

	public String getAvaibleCards() {
		return availableCards.toString();
	}
	
	public String getChosenCard(int index){
		return availableCards.getCards().get(index).toString();
	}

	public Action createAction(String chosenCity, int chosenCard) {
		return new BuildEmporiumPermitTile(citiesMap.get(chosenCity), chosenCard);
	}
	

}
