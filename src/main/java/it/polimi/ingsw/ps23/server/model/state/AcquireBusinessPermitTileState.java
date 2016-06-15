package it.polimi.ingsw.ps23.server.model.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.server.model.Game;
import it.polimi.ingsw.ps23.server.model.actions.AcquireBusinessPermitTile;
import it.polimi.ingsw.ps23.server.model.actions.Action;
import it.polimi.ingsw.ps23.server.model.map.Region;
import it.polimi.ingsw.ps23.server.model.map.regions.GroupRegionalCity;
import it.polimi.ingsw.ps23.server.model.player.HandDeck;
import it.polimi.ingsw.ps23.server.model.player.PoliticHandDeck;
import it.polimi.ingsw.ps23.server.view.ViewVisitor;

public class AcquireBusinessPermitTileState extends ActionState {

	private static final int MAX_CARDS_NUMBER = 4;
	
	private HandDeck politicHandDeck;
	private Map<String, Region> regionsMap;
	
	AcquireBusinessPermitTileState(String name) {
		super(name);
		regionsMap = new HashMap<>();
	}

	public String getPoliticHandDeck() {
		return politicHandDeck.toString();
	}
	
	public String getCouncilsMap() {
		return regionsMap.toString();
	}
	
	public int getAvailablePoliticCardsNumber(String chosenCouncil) {
		politicHandDeck = ((PoliticHandDeck)politicHandDeck).getAvailableCards(((GroupRegionalCity)regionsMap.get(chosenCouncil)).getCouncil());
		if(politicHandDeck.getHandSize() > MAX_CARDS_NUMBER) {
			return MAX_CARDS_NUMBER;
		}
		return politicHandDeck.getHandSize();
	}
		
	public String getAvailablePermitTile(String chosenCouncil) {
		return ((GroupRegionalCity)regionsMap.get(chosenCouncil)).getPermissionDeckUp().toString();
	}

	public Action createAction(String chosenCouncil, List<String> removedPoliticCards, int chosenPermissionCard) {
		return new AcquireBusinessPermitTile(removedPoliticCards, (GroupRegionalCity)regionsMap.get(chosenCouncil), chosenPermissionCard);
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
