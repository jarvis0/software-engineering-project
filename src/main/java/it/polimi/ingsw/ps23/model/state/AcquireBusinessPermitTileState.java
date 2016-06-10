package it.polimi.ingsw.ps23.model.state;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.ps23.model.Game;
import it.polimi.ingsw.ps23.model.HandDeck;
import it.polimi.ingsw.ps23.model.PoliticHandDeck;
import it.polimi.ingsw.ps23.model.actions.AcquireBusinessPermitTile;
import it.polimi.ingsw.ps23.model.actions.Action;
import it.polimi.ingsw.ps23.model.map.GroupRegionalCity;
import it.polimi.ingsw.ps23.model.map.Region;
import it.polimi.ingsw.ps23.view.ViewVisitor;

public class AcquireBusinessPermitTileState extends ActionState {

	private HandDeck politicHandDeck;
	private Map<String, Region> regionsMap;
	int initialNobilityTrackPoints;
	
	public AcquireBusinessPermitTileState(String name) {
		super(name);
		regionsMap = new HashMap<>();
	}

	@Override
	public void changeState(Context context, Game game) {
		context.setState(this);
		initialNobilityTrackPoints = game.getCurrentPlayer().getNobilityTrackPoints();
		politicHandDeck = new PoliticHandDeck(game.getCurrentPlayer().getPoliticHandDeck().getCards());
		regionsMap = game.getGameMap().getRegionMap();
	}

	public String getPoliticHandDeck() {
		return politicHandDeck.toString();
	}
	
	public String getCouncilsMap() {
		return regionsMap.toString();
	}
	
	public int getAvailablePoliticCardsNumber(String chosenCouncil) {
		politicHandDeck = ((PoliticHandDeck)politicHandDeck).getAvailableCards(((GroupRegionalCity)regionsMap.get(chosenCouncil)).getCouncil());
		return politicHandDeck.getHandSize();
	}
		
	public String getAvailablePermitTile(String chosenCouncil) {
		return ((GroupRegionalCity)regionsMap.get(chosenCouncil)).getPermissionDeckUp().toString();
	}
	
	@Override
	public void acceptView(ViewVisitor view) {
		view.visit(this);
	}
	
	public Action createAction(String chosenCouncil, List<String> removedPoliticCards, int chosenPermissionCard) {
		return new AcquireBusinessPermitTile(removedPoliticCards, (GroupRegionalCity)regionsMap.get(chosenCouncil), chosenPermissionCard, initialNobilityTrackPoints);
	}

}
