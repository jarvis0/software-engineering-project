package it.polimi.ingsw.ps23.server.model.state;

import java.util.Map;

import it.polimi.ingsw.ps23.server.commons.exceptions.IllegalActionSelected;
import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.actions.BuildEmporiumPermitTile;
import it.polimi.ingsw.ps23.server.model.map.regions.City;
import it.polimi.ingsw.ps23.server.model.player.HandDeck;
import it.polimi.ingsw.ps23.server.model.player.PermissionHandDeck;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class BuildEmporiumPermitTileState extends ActionState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8070870675842800922L;
	private HandDeck availableCards;
	private Map<String, City> citiesMap;
	
	BuildEmporiumPermitTileState(String name) {
		super(name);
	}

	public String getAvaibleCards() throws IllegalActionSelected {
		if (availableCards.getHandSize() == 0) {
			throw new IllegalActionSelected();
		}
		return availableCards.toString();
	}
	
	public String getChosenCard(int index){
		return availableCards.getCards().get(index).toString();
	}

	public Action createAction(String chosenCity, int chosenCard) {
		return new BuildEmporiumPermitTile(citiesMap.get(chosenCity), chosenCard);
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

}
